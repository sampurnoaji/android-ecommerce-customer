package id.io.android.olebsai.domain.model.category

import id.io.android.olebsai.R

enum class CategoryType(val stringRes: Int, val imageRes: Int) {
    ALL(R.string.category_all, R.drawable.ic_diversity),
    CRAFT(R.string.category_craft, R.drawable.ic_craft),
    FASHION(R.string.category_fashion, R.drawable.ic_fashion),
    CULINARY(R.string.category_culinary, R.drawable.ic_culinary),
    ELECTRONIC(R.string.category_electronic, R.drawable.ic_electronic),
    TOPUP(R.string.category_topup, R.drawable.ic_electricity)
}