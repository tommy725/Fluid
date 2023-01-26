package com.raycai.fluffie.util

import com.raycai.fluffie.data.model.Con2
import com.raycai.fluffie.data.model.Pros
import com.raycai.fluffie.data.model.Review

open class AppConst {

    companion object {
        //=== Start of user filter values ===
        val MAX_USER_FILTER_TOPIC_SELECTIONS = 3

        val USER_FILTER_SKIN_TYPE = "uf_skin_type"
        val USER_FILTER_AGE = "uf_age"
        val USER_FILTER_BEFEFITS = "uf_benefits"
        val USER_FILTER_CONSISTENCIES = "uf_consistencies"
        val USER_FILTER_FRAGRANCE = "uf_fragrance"
        val USER_FILTER_ACNE_PRONE = "uf_acne_prone"
        val ACNE_PRONE = "\uD83D\uDE14 Acne prone"

        val defaultSkinType = "\uD83C\uDF35 Dry"

        val skinTypes = arrayListOf(
            "\uD83E\uDE94 Oily",
            "\uD83C\uDF35 Dry",
            "\uD83D\uDC4C Normal",
            "\uD83E\uDD1D Combination",
            "\uD83D\uDE30 Sensitive"
        )

        val ages = arrayListOf(
            "17 and under",
            "18 to 24",
            "25 to 34",
            "35 to 44",
            "45 to 54",
            "55 to 64",
            "65 and over"
        )

        val benefits = arrayListOf(
            "Less breakouts",
            "Smaller pores",
            "Better skin texture",
            "Reduced appearance of scars",
            "Reduced pigmentation",
            "Brighter skin",
            "Reduced fine lines & wrinkles",
            "Less skin oiliness",
            "Reduced redness",
            "Plumped skin",
            "Reduced skin puffiness",
            "Tighter skin",
            "Hydrated skin",
            "Removed makeup well",
            "Cleansed skin well",
            "More even skin tone",
            "Helps get rid of pimples"
        )

        val productConsistencies = arrayListOf(
            "Heavy",
            "Light",
            "Not sticky",
            "Foamy",
            "Grainy",
            "Creamy",
            "Mousse",
            "Liquid",
            "Rich",
            "Gel",
            "Thin",
            "Solid",
            "Clay"
        )

        val fragrance = arrayListOf(
            "Nice fragrance",
            "Fragrance free"
        )
        //=== End of user filter values ===

        val prosOrCon = arrayListOf(
            "Pros",
            "Cons"
        )

        val locations = arrayListOf(
            "All",
            "NZ & AU"
        )

        val productAspects = arrayListOf(
            "Fragrance",
            "Texture"
        )

        val reviewSource = arrayListOf(
            "Select all",
            "www.fluffie.com",
            "www.hikoco.co.nz",
            "www.sephora.co.nz",
            "www.paulaschoice.com.au",
            "www.mecca.co.nz",
            "www.ednibody.co.nz"
        )

        val fluffieFilter = arrayListOf(
            "On",
            "Off"
        )

        val prosList = arrayListOf(
            Pros("Smoother skin", "21"),
            Pros("Brighter skin ", "2"),
            Pros("Evened tone", "12"),
            Pros("Softer skin", "31"),
            Pros("Moisturising", "17"),
            Pros("Smoother skin", "10"),
            Pros("Brighter skin ", "4"),
            Pros("Evened tone", "1"),
            Pros("Softer skin", "6"),
            Pros("Moisturising", "7")
        )

        val consList = arrayListOf(
            Con2("Caused breakouts", "21"),
            Con2("Was irritating", "1"),
            Con2("Dried skin out", "3"),
            Con2("Caused peeling", "5"),
            Con2("Caused sensitivity", "6"),
            Con2("Caused breakouts", "7"),
            Con2("Was irritating", "8"),
            Con2("Dried skin out", "9"),
            Con2("Caused peeling", "11"),
            Con2("Caused sensitivity", "22")
        )

        val reviews = arrayListOf(
            Review(
                3.5,
                "1 day ago",
                "The CEO Serum and Oil being the stars of the show, my skin is just glowing",
                "jonty415 - mecca.co.nz"
            ),
            Review(
                3.0,
                "2 day ago",
                "the glow i’ve been having right now is making me so happy 2 years",
                "joafs445 - mecca.co.nz"
            ),
            Review(
                4.0,
                "3 day ago",
                "Moreover, what a transformation on my face, feels hydrated and glowy",
                "haily51 - mecca.co.nz"
            ),
            Review(
                3.5,
                "1 day ago",
                "The CEO Serum and Oil being the stars of the show, my skin is just glowing",
                "jonty415 - mecca.co.nz"
            ),
            Review(
                3.0,
                "2 day ago",
                "the glow i’ve been having right now is making me so happy 2 years",
                "joafs445 - mecca.co.nz"
            ),
            Review(
                4.0,
                "3 day ago",
                "Moreover, what a transformation on my face, feels hydrated and glowy",
                "haily51 - mecca.co.nz"
            )

        )

    }
}