package com.raycai.fluffie.ui.home.product.review

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.dam.bestexpensetracker.util.AppLog
import com.devlomi.record_view.OnRecordListener
import com.permissionx.guolindev.PermissionX
import com.raycai.fluffie.GalleryActivity
import com.raycai.fluffie.HomeActivity
import com.raycai.fluffie.R
import com.raycai.fluffie.adapter.ReviewAttachmentPagerAdapter
import com.raycai.fluffie.app.MyApp
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.data.model.ReviewAttachment
import com.raycai.fluffie.data.model.SelectedFile
import com.raycai.fluffie.databinding.FragmentReviewBinding
import com.raycai.fluffie.ui.home.product.review.attachment.AttachmentListener
import java.io.File
import java.util.*

class ReviewFragment : BaseFragment() {

    private val TAG = ReviewFragment::class.java.simpleName
    private lateinit var viewModel: ReviewViewModel
    private lateinit var binding: FragmentReviewBinding
    val recordTimeLimit = 30 //seconds

    //recorder
    private var mediaRecorder: MediaRecorder? = null

    //player
    private var mediaPlayer: MediaPlayer? = null

    var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>? = null
    private var attachmentPagerAdapter: ReviewAttachmentPagerAdapter? = null
    private var recordFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ReviewViewModel::class.java]
        binding = FragmentReviewBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        viewModel.product = (activity as HomeActivity).selectedProduct
        setObservers()
        initData()
        initUi()
        initFilePicker()
        return binding.root
    }

    private fun initFilePicker() {
        pickMedia =
            registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uri ->
                if (uri != null) {
                    MyApp.instance.selectedFiles.clear()
                    uri.forEach {
                        val mimeType: String? = uri.let { returnUri ->
                            (activity as HomeActivity).contentResolver.getType(it)
                        }
                        log("File Type : $mimeType")

                        val stringUrl = "$it"

                        log("File Type : $stringUrl")

                        val f = SelectedFile()
                        f.url = stringUrl
                        f.mimeType = mimeType

                        MyApp.instance.selectedFiles.add(f)
                    }
                } else {
                    log("No media selected")
                }
            }
    }

    private fun startRecording() {
        log("startRecording()")
        val parent: String = "${requireContext().cacheDir}${File.separator}recordings"
        recordFile = File(parent, "${Date().time}.wav")

        try {
            if (!File(parent).exists()) {
                File(parent).mkdirs()
            }

            if (!recordFile?.exists()!!) {
                recordFile?.createNewFile()
            }

            mediaRecorder?.prepare()
        } catch (ex: Exception) {
            log("Failed to prepare")
            ex.printStackTrace()
        }

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setAudioEncodingBitRate(16 * 44100)
            setAudioSamplingRate(44100)
            setOutputFile(recordFile?.path)

            try {
                prepare()
            } catch (e: Exception) {
                log("prepare() failed")
            }

            start()
        }
    }

    private val seekBarListener: OnSeekBarChangeListener = object : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            if (fromUser) {
                mediaPlayer!!.seekTo(progress)
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
            binding.ivPlay.setImageResource(R.drawable.ic_play)
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            binding.ivPlay.setImageResource(R.drawable.ic_pause)
            mediaPlayer!!.start()
        }
    }

    private fun stopRecording() {
        try {
            mediaRecorder?.apply {
                stop()
                release()
                binding.tvCurrentTime.text = "00:00"
            }
            mediaRecorder = null
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun initUi() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_MEDIA_LOCATION
            )
            .request { allGranted, grantedList, deniedList ->
                var audioPermissionGranted = false

                grantedList.forEach {
                    if (it != null) {
                        if (it == Manifest.permission.RECORD_AUDIO) {
                            audioPermissionGranted = true
                        }
                    }
                }

                if (recordFile == null) {
                    if (audioPermissionGranted) {
                        binding.recordView.visibility = View.VISIBLE
                        binding.recordButton.visibility = View.VISIBLE
                    } else {
                        binding.recordView.visibility = View.INVISIBLE
                        binding.recordButton.visibility = View.INVISIBLE
                        showToast("Required permissions are denied. Please grant permission from settings.")
                    }
                }
            }

        binding.recordButton.setRecordView(binding.recordView)
        binding.recordView.setLessThanSecondAllowed(true)
        binding.recordView.timeLimit = (recordTimeLimit * 1000).toLong()
        binding.recordView.setCounterTimeColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        binding.recordView.setTrashIconColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        binding.recordView.setOnRecordListener(object : OnRecordListener {
            override fun onStart() {
                log("Start Recording...")
                startRecording()
            }

            override fun onCancel() {
                //On Swipe To Cancel
                log("Cancel recording.")
                stopRecording()
            }

            override fun onFinish(recordTime: Long, limitReached: Boolean) {
                log("Stop recording. : recordTime: $recordTime")
                stopRecording()
                onRecordingFinished()
            }

            override fun onLessThanSecond() {
                log("on less one second.")
            }
        })

        viewModel.updateRatingText()

        binding.materialRatingBar.setOnRatingChangeListener { ratingBar, rating ->
            viewModel.rating = rating.toDouble()
        }

        binding.seekBarTime.setOnSeekBarChangeListener(seekBarListener)
        binding.seekBarTime.progressDrawable.setColorFilter(
            ContextCompat.getColor(
                requireContext(),
                R.color.rose
            ), PorterDuff.Mode.SRC_IN
        )
        binding.seekBarTime.thumb.setColorFilter(
            ContextCompat.getColor(
                requireContext(),
                R.color.rose
            ), PorterDuff.Mode.SRC_IN
        );
    }

    private fun onRecordingFinished() {
        binding.recordView.visibility = View.GONE
        binding.recordButton.visibility = View.GONE
        binding.layoutPlayer.visibility = View.VISIBLE
        binding.layoutAudioText.visibility = View.VISIBLE

        //init media player
        try {
            binding.seekBarTime.progress = 0
            mediaPlayer = MediaPlayer()

            try {
                mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
                mediaPlayer?.setDataSource(requireContext(), Uri.parse(recordFile?.path))
                mediaPlayer?.prepare()
            } catch (ex: Exception) {
                log("Unable to play")
                ex.printStackTrace()
            }

            mediaPlayer?.setOnPreparedListener { mp ->
                binding.seekBarTime.max = mediaPlayer?.duration!!
                binding.tvTotalTime.text = convertTime(mediaPlayer?.duration!! / 1000)
            }

            mediaPlayer?.setOnCompletionListener {
                binding.ivPlay.setImageResource(R.drawable.ic_play)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            showToast("Error while preparing media player.")
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initData()
    }

    private fun setObservers() {
        viewModel.actionEnabled.observeForever {
            binding.btnNext.isEnabled = it
        }

        viewModel.attachmentChanged.observeForever {
            setAttachments()
        }

        viewModel.loadGallery.observeForever {
            loadGallery()
        }
    }

    private fun setAttachments() {
        attachmentPagerAdapter =
            ReviewAttachmentPagerAdapter(viewModel.attachments, childFragmentManager)
        binding.viewPager.adapter = attachmentPagerAdapter
        binding.viewPager.clipToPadding = false
        binding.viewPager.setPadding(260, 0, 260, 0)
        binding.viewPager.pageMargin = 4.dpToPx.toInt()
        binding.viewPager.offscreenPageLimit = 1
        binding.dotsIndicator.attachTo(binding.viewPager)

        (activity as HomeActivity).reviewAttachmentListener = object : AttachmentListener {
            override fun onClicked(a: ReviewAttachment) {
                if (a.isAddAttachment()) {
                    viewModel.onAttachClicked()
                }
            }

            override fun onRemoveAttachment(a: SelectedFile?) {
                viewModel.onRemoveAttachment(a)
            }
        }
    }

    private fun loadGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pickMedia?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        } else {
            startActivity(Intent(requireContext(), GalleryActivity::class.java))
        }
    }

    private fun initData() {
        viewModel.rating = MyApp.instance.rating
        recordFile = MyApp.instance.recording
        binding.materialRatingBar.rating = viewModel.rating.toFloat()

        viewModel.initData()

        if (recordFile != null) {
            onRecordingFinished()
        }
    }

    fun onPlayBtnClicked(view: View) {
        log("onPlayBtnClicked()")
        try {
            if (mediaPlayer != null && mediaPlayer?.isPlaying!!) {
                log("stop playing")
                mediaPlayer?.pause()
                binding.ivPlay.setImageResource(R.drawable.ic_play)
            } else {
                log("start playing")
                mediaPlayer?.start()

                try {
                    update(
                        mediaPlayer!!,
                        binding.tvCurrentTime,
                        binding.tvTotalTime,
                        binding.seekBarTime,
                        requireContext()
                    )
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    showToast("Error while updating audio info.")
                }

                binding.ivPlay.setImageResource(R.drawable.ic_pause)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            showToast("Error while playing.")
        }
    }

    fun onNextClicked(view: View) {
        MyApp.instance.recording = recordFile
        MyApp.instance.rating = viewModel.rating
        (activity as HomeActivity).loadReviewConfirmFragment(true)
    }

    private fun convertTime(seconds: Int): String? {
        val s = seconds % 60
        val m = seconds / 60 % 60
        return String.format("%02d:%02d", m, s)
    }

    private fun update(
        mediaPlayer: MediaPlayer,
        tvCurrentTime: TextView,
        tvTotal: TextView,
        seekBar: SeekBar,
        context: Context
    ) {
        (context as Activity).runOnUiThread {
            seekBar.progress = mediaPlayer.currentPosition
            if (mediaPlayer.duration - mediaPlayer.currentPosition > 100) {
                tvCurrentTime.text = convertTime(mediaPlayer.currentPosition / 1000)
                tvTotal.text = convertTime(mediaPlayer.duration / 1000)
            } else {
                tvTotal.text = convertTime(mediaPlayer.duration / 1000)
                seekBar.progress = 0
            }
            val handler = Handler()
            try {
                val runnable = Runnable {
                    try {
                        if (mediaPlayer.currentPosition > -1) {
                            try {
                                update(mediaPlayer, tvCurrentTime, tvTotal, seekBar, context)
                            } catch (e: java.lang.Exception) {
                                e.printStackTrace()
                            }
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
                handler.postDelayed(runnable, 2)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    fun stopRecordAndPlayback() {
        stopRecording()

        try {
            if (mediaPlayer != null) {
                if (mediaPlayer?.isPlaying!!) {
                    mediaPlayer?.pause()
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                }
            }

            binding.recordView.cancelRecord()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun goBack(view: View) {
        (activity as HomeActivity).onBackPressed()
    }

    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

}