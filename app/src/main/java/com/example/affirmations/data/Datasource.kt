/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.affirmations.data

import com.example.affirmations.R
import com.example.affirmations.model.Affirmation
import com.example.affirmations.model.Postcard

class Datasource() {
    fun loadAffirmations(): List<Affirmation> {
        return listOf<Affirmation>(
            Affirmation(R.string.affirmation1,   R.drawable.image1, "This is a comment test01"),
            Affirmation(R.string.affirmation2,   R.drawable.image2, "This is a comment test02"),
            Affirmation(R.string.affirmation3,   R.drawable.image3, "This is a comment test03"),
            Affirmation(R.string.affirmation4,   R.drawable.image4, "This is a comment test04"),
            Affirmation(R.string.affirmation5,   R.drawable.image5, "This is a comment test05"),
            Affirmation(R.string.affirmation6,   R.drawable.image6, "This is a comment test06"),
            Affirmation(R.string.affirmation7,   R.drawable.image7, "This is a comment test07"),
            Affirmation(R.string.affirmation8,   R.drawable.image8, "This is a comment test08"),
            Affirmation(R.string.affirmation9,   R.drawable.image9, "This is a comment test09"),
            Affirmation(R.string.affirmation10, R.drawable.image10, "This is a comment test10"))
    }
    fun loadPostcards(): List<Postcard> {
        return listOf<Postcard>(
            Postcard("Seoul",   "https://www.shutterstock.com/zh-Hant/blog/wp-content/uploads/sites/11/2018/12/shutterstock_438078565.jpg?w=760", "This is a comment test02","2024/12/21","2024/06/01"),
            Postcard("Hawaii",  "res/drawable/image2.jpg", "This is a comment test01","2025/01/01","2024/12/21"),
            Postcard("Tokyo",   "res/drawable/image3.jpg", "This is a comment test03","2024/12/21","2024/06/01"),
            Postcard("Shanghai","res/drawable/image4.jpg", "This is a comment test04","2025/01/01","2024/12/21"),
            Postcard("Taipei",  "res/drawable/image5.jpg", "This is a comment test05","2024/12/21","2024/06/01"),
            Postcard("London",  "res/drawable/image6.jpg", "This is a comment test06","2025/01/01","2024/12/21"),
            Postcard("Paris",   "res/drawable/image7.jpg", "This is a comment test07","2024/12/21","2024/06/01")


        )
    }
}
