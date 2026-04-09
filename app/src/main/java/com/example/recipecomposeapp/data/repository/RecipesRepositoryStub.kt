package com.example.recipecomposeapp.data.repository

import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.data.model.IngredientDto
import com.example.recipecomposeapp.data.model.RecipeDto

object RecipesRepositoryStub {

    private val categories: List<CategoryDto> = listOf(
        CategoryDto(
            id = 0,
            title = "Бургеры",
            description = "Рецепты всех популярных видов бургеров",
            imageUrl = "burger.jpg",
        ),
        CategoryDto(
            id = 1,
            title = "Десерты",
            description = "Самые вкусные рецепты десертов специально для вас",
            imageUrl = "dessert.jpg",
        ),
        CategoryDto(
            id = 2,
            title = "Пицца",
            description = "Пицца на любой вкус и цвет. Лучшая подборка для тебя",
            imageUrl = "pizza.jpg",
        ),
        CategoryDto(
            id = 3,
            title = "Рыба",
            description = "Печеная, жареная, сушеная, любая рыба на твой вкус",
            imageUrl = "fish.jpg",
        ),
        CategoryDto(
            id = 4,
            title = "Супы",
            description = "От классики до экзотики: мир в одной тарелке",
            imageUrl = "soup.jpg",
        ),
        CategoryDto(
            id = 5,
            title = "Салаты",
            description = "Хрустящий калейдоскоп под соусом вдохновения",
            imageUrl = "salad.jpg",
        ),
    )

    private val burgerRecipes: List<RecipeDto> = listOf(
        RecipeDto(
            id = 0,
            title = "Классический бургер с говядиной",
            ingredients = listOf(
                IngredientDto(
                    quantity = "0.5",
                    unitOfMeasure = "кг",
                    description = "говяжий фарш"
                ),
                IngredientDto(
                    quantity = "1.0",
                    unitOfMeasure = "шт",
                    description = "луковица, мелко нарезанная"
                ),
                IngredientDto(
                    quantity = "2.0",
                    unitOfMeasure = "зубч",
                    description = "чеснок, измельченный"
                ),
                IngredientDto(
                    quantity = "4.0",
                    unitOfMeasure = "шт",
                    description = "булочки для бургера"
                ),
                IngredientDto(
                    quantity = "4.0",
                    unitOfMeasure = "шт",
                    description = "листа салата"
                ),
                IngredientDto(
                    quantity = "1.0",
                    unitOfMeasure = "шт",
                    description = "помидор, нарезанный кольцами"
                ),
                IngredientDto(
                    quantity = "2.0",
                    unitOfMeasure = "ст. л.",
                    description = "горчица"
                ),
                IngredientDto(
                    quantity = "2.0",
                    unitOfMeasure = "ст. л.",
                    description = "кетчуп"
                ),
                IngredientDto(
                    quantity = "по вкусу",
                    unitOfMeasure = "",
                    description = "соль и черный перец"
                )
            ),
            method = listOf(
                "В глубокой миске смешайте говяжий фарш, лук, чеснок, соль и перец. Разделите фарш на 4 равные части и сформируйте котлеты.",
                "Разогрейте сковороду на среднем огне. Обжаривайте котлеты с каждой стороны в течение 4-5 минут или до желаемой степени прожарки.",
                "В то время как котлеты готовятся, подготовьте булочки. Разрежьте их пополам и обжарьте на сковороде до золотистой корочки.",
                "Смазать нижние половинки булочек горчицей и кетчупом, затем положите лист салата, котлету, кольца помидора и закройте верхней половинкой булочки.",
                "Подавайте бургеры горячими с картофельными чипсами или картофельным пюре."
            ),
            imageUrl = "burger-hamburger.jpg"
        ),
        RecipeDto(
            id = 1,
            title = "Чизбургер с беконом",
            ingredients = listOf(
                IngredientDto(
                    quantity = "0.4",
                    unitOfMeasure = "кг",
                    description = "говяжий фарш"
                ),
                IngredientDto(
                    quantity = "4.0",
                    unitOfMeasure = "шт",
                    description = "ломтика бекона"
                ),
                IngredientDto(
                    quantity = "4.0",
                    unitOfMeasure = "шт",
                    description = "ломтика сыра чеддер"
                ),
                IngredientDto(
                    quantity = "4.0",
                    unitOfMeasure = "шт",
                    description = "булочки для бургера"
                ),
                IngredientDto(
                    quantity = "1.0",
                    unitOfMeasure = "шт",
                    description = "помидор, нарезанный"
                ),
                IngredientDto(
                    quantity = "по вкусу",
                    unitOfMeasure = "",
                    description = "майонез и кетчуп"
                )
            ),
            method = listOf(
                "Обжарьте бекон на сковороде до хрустящей корочки, отложите на бумажное полотенце.",
                "Сформируйте из фарша 4 котлеты, обжарьте с каждой стороны по 4 минуты.",
                "За минуту до готовности положите на каждую котлету по ломтику сыра, чтобы он расплавился.",
                "Соберите бургер: булочка, майонез, котлета с сыром, бекон, помидор, кетчуп.",
                "Подавайте горячими."
            ),
            imageUrl = "burger-cheeseburger.jpg"
        )
    )

    private val dessertRecipes: List<RecipeDto> = listOf(
        RecipeDto(
            id = 2,
            title = "Шоколадный брауни",
            ingredients = listOf(
                IngredientDto(
                    quantity = "200",
                    unitOfMeasure = "г",
                    description = "темный шоколад"
                ),
                IngredientDto(
                    quantity = "150",
                    unitOfMeasure = "г",
                    description = "сливочное масло"
                ),
                IngredientDto(
                    quantity = "3",
                    unitOfMeasure = "шт",
                    description = "яйца"
                ),
                IngredientDto(
                    quantity = "180",
                    unitOfMeasure = "г",
                    description = "сахар"
                ),
                IngredientDto(
                    quantity = "100",
                    unitOfMeasure = "г",
                    description = "мука"
                ),
                IngredientDto(
                    quantity = "30",
                    unitOfMeasure = "г",
                    description = "какао-порошок"
                )
            ),
            method = listOf(
                "Растопите шоколад и сливочное масло на водяной бане.",
                "Взбейте яйца с сахаром до однородности.",
                "Соедините шоколадную смесь с яйцами, добавьте муку и какао.",
                "Перелейте тесто в форму и выпекайте 25-30 минут при 180°C.",
                "Остудите, нарежьте квадратами и подавайте."
            ),
            imageUrl = "img_placeholder.jpg"
        ),
        RecipeDto(
            id = 3,
            title = "Чизкейк без выпечки",
            ingredients = listOf(
                IngredientDto(
                    quantity = "300",
                    unitOfMeasure = "г",
                    description = "печенье"
                ),
                IngredientDto(
                    quantity = "120",
                    unitOfMeasure = "г",
                    description = "сливочное масло"
                ),
                IngredientDto(
                    quantity = "400",
                    unitOfMeasure = "г",
                    description = "сливочный сыр"
                ),
                IngredientDto(
                    quantity = "200",
                    unitOfMeasure = "мл",
                    description = "сливки"
                ),
                IngredientDto(
                    quantity = "100",
                    unitOfMeasure = "г",
                    description = "сахарная пудра"
                ),
                IngredientDto(
                    quantity = "10",
                    unitOfMeasure = "г",
                    description = "желатин"
                )
            ),
            method = listOf(
                "Измельчите печенье и смешайте с растопленным маслом.",
                "Утрамбуйте основу в форму и уберите в холодильник.",
                "Взбейте сливочный сыр, сливки и сахарную пудру.",
                "Добавьте подготовленный желатин и перемешайте.",
                "Вылейте начинку на основу и охладите до застывания."
            ),
            imageUrl = "img_placeholder.jpg"
        )
    )

    private val pizzaRecipes: List<RecipeDto> = listOf(
        RecipeDto(
            id = 4,
            title = "Пицца Маргарита",
            ingredients = listOf(
                IngredientDto(
                    quantity = "1",
                    unitOfMeasure = "шт",
                    description = "основа для пиццы"
                ),
                IngredientDto(
                    quantity = "150",
                    unitOfMeasure = "г",
                    description = "моцарелла"
                ),
                IngredientDto(
                    quantity = "100",
                    unitOfMeasure = "г",
                    description = "томатный соус"
                ),
                IngredientDto(
                    quantity = "2",
                    unitOfMeasure = "шт",
                    description = "помидора"
                ),
                IngredientDto(
                    quantity = "по вкусу",
                    unitOfMeasure = "",
                    description = "свежий базилик"
                )
            ),
            method = listOf(
                "Раскатайте основу и смажьте томатным соусом.",
                "Выложите кружочки помидоров и кусочки моцареллы.",
                "Выпекайте 10-12 минут при 220°C.",
                "Добавьте свежий базилик перед подачей."
            ),
            imageUrl = "pizza_margarita.jpg"
        ),
        RecipeDto(
            id = 5,
            title = "Пепперони",
            ingredients = listOf(
                IngredientDto(
                    quantity = "1",
                    unitOfMeasure = "шт",
                    description = "основа для пиццы"
                ),
                IngredientDto(
                    quantity = "120",
                    unitOfMeasure = "г",
                    description = "сыр моцарелла"
                ),
                IngredientDto(
                    quantity = "80",
                    unitOfMeasure = "г",
                    description = "колбаса пепперони"
                ),
                IngredientDto(
                    quantity = "100",
                    unitOfMeasure = "г",
                    description = "томатный соус"
                )
            ),
            method = listOf(
                "Подготовьте основу для пиццы и смажьте ее соусом.",
                "Посыпьте моцареллой и равномерно разложите пепперони.",
                "Выпекайте в разогретой духовке 10-15 минут при 220°C.",
                "Подавайте горячей."
            ),
            imageUrl = "pizza_pepperoni.jpg"
        )
    )

    private val fishRecipes: List<RecipeDto> = listOf(
        RecipeDto(
            id = 6,
            title = "Запеченный лосось с лимоном",
            ingredients = listOf(
                IngredientDto(
                    quantity = "2",
                    unitOfMeasure = "шт",
                    description = "стейка лосося"
                ),
                IngredientDto(
                    quantity = "1",
                    unitOfMeasure = "шт",
                    description = "лимон"
                ),
                IngredientDto(
                    quantity = "2",
                    unitOfMeasure = "ст. л.",
                    description = "оливковое масло"
                ),
                IngredientDto(
                    quantity = "по вкусу",
                    unitOfMeasure = "",
                    description = "соль и черный перец"
                ),
                IngredientDto(
                    quantity = "по вкусу",
                    unitOfMeasure = "",
                    description = "свежий укроп"
                )
            ),
            method = listOf(
                "Выложите стейки лосося в форму для запекания.",
                "Сбрызните оливковым маслом, добавьте соль, перец и ломтики лимона.",
                "Запекайте 15-20 минут при 200°C.",
                "Перед подачей посыпьте укропом."
            ),
            imageUrl = "img_placeholder.jpg"
        ),
        RecipeDto(
            id = 7,
            title = "Треска в сливочном соусе",
            ingredients = listOf(
                IngredientDto(
                    quantity = "500",
                    unitOfMeasure = "г",
                    description = "филе трески"
                ),
                IngredientDto(
                    quantity = "200",
                    unitOfMeasure = "мл",
                    description = "сливки"
                ),
                IngredientDto(
                    quantity = "1",
                    unitOfMeasure = "шт",
                    description = "луковица"
                ),
                IngredientDto(
                    quantity = "1",
                    unitOfMeasure = "ст. л.",
                    description = "сливочное масло"
                ),
                IngredientDto(
                    quantity = "по вкусу",
                    unitOfMeasure = "",
                    description = "соль и специи"
                )
            ),
            method = listOf(
                "Обжарьте лук на сливочном масле до мягкости.",
                "Добавьте филе трески и слегка обжарьте.",
                "Влейте сливки, посолите и добавьте специи.",
                "Тушите 10-12 минут до готовности рыбы."
            ),
            imageUrl = "img_placeholder.jpg"
        )
    )

    private val soupRecipes: List<RecipeDto> = listOf(
        RecipeDto(
            id = 8,
            title = "Куриный суп с лапшой",
            ingredients = listOf(
                IngredientDto(
                    quantity = "500",
                    unitOfMeasure = "г",
                    description = "куриное филе"
                ),
                IngredientDto(
                    quantity = "2",
                    unitOfMeasure = "шт",
                    description = "картофелины"
                ),
                IngredientDto(
                    quantity = "1",
                    unitOfMeasure = "шт",
                    description = "морковь"
                ),
                IngredientDto(
                    quantity = "1",
                    unitOfMeasure = "шт",
                    description = "луковица"
                ),
                IngredientDto(
                    quantity = "100",
                    unitOfMeasure = "г",
                    description = "лапша"
                )
            ),
            method = listOf(
                "Отварите куриное филе до готовности и достаньте из бульона.",
                "Добавьте в бульон картофель, лук и морковь.",
                "Через 10 минут добавьте лапшу и нарезанную курицу.",
                "Варите до готовности лапши, подавайте горячим."
            ),
            imageUrl = "img_placeholder.jpg"
        ),
        RecipeDto(
            id = 9,
            title = "Томатный суп",
            ingredients = listOf(
                IngredientDto(
                    quantity = "500",
                    unitOfMeasure = "г",
                    description = "помидоры"
                ),
                IngredientDto(
                    quantity = "1",
                    unitOfMeasure = "шт",
                    description = "луковица"
                ),
                IngredientDto(
                    quantity = "2",
                    unitOfMeasure = "зубч",
                    description = "чеснок"
                ),
                IngredientDto(
                    quantity = "500",
                    unitOfMeasure = "мл",
                    description = "овощной бульон"
                ),
                IngredientDto(
                    quantity = "2",
                    unitOfMeasure = "ст. л.",
                    description = "оливковое масло"
                )
            ),
            method = listOf(
                "Обжарьте лук и чеснок на оливковом масле.",
                "Добавьте нарезанные помидоры и тушите 10 минут.",
                "Влейте овощной бульон и варите еще 15 минут.",
                "Измельчите суп блендером до однородности."
            ),
            imageUrl = "img_placeholder.jpg"
        )
    )

    private val saladRecipes: List<RecipeDto> = listOf(
        RecipeDto(
            id = 10,
            title = "Греческий салат",
            ingredients = listOf(
                IngredientDto(
                    quantity = "2",
                    unitOfMeasure = "шт",
                    description = "помидора"
                ),
                IngredientDto(
                    quantity = "1",
                    unitOfMeasure = "шт",
                    description = "огурец"
                ),
                IngredientDto(
                    quantity = "1",
                    unitOfMeasure = "шт",
                    description = "сладкий перец"
                ),
                IngredientDto(
                    quantity = "100",
                    unitOfMeasure = "г",
                    description = "сыр фета"
                ),
                IngredientDto(
                    quantity = "50",
                    unitOfMeasure = "г",
                    description = "маслины"
                ),
                IngredientDto(
                    quantity = "2",
                    unitOfMeasure = "ст. л.",
                    description = "оливковое масло"
                )
            ),
            method = listOf(
                "Нарежьте помидоры, огурец и сладкий перец крупными кусочками.",
                "Добавьте маслины и кубики феты.",
                "Полейте оливковым маслом и аккуратно перемешайте."
            ),
            imageUrl = "img_placeholder.jpg"
        ),
        RecipeDto(
            id = 11,
            title = "Цезарь с курицей",
            ingredients = listOf(
                IngredientDto(
                    quantity = "200",
                    unitOfMeasure = "г",
                    description = "куриное филе"
                ),
                IngredientDto(
                    quantity = "1",
                    unitOfMeasure = "шт",
                    description = "романо или айсберг"
                ),
                IngredientDto(
                    quantity = "50",
                    unitOfMeasure = "г",
                    description = "пармезан"
                ),
                IngredientDto(
                    quantity = "100",
                    unitOfMeasure = "г",
                    description = "сухарики"
                ),
                IngredientDto(
                    quantity = "3",
                    unitOfMeasure = "ст. л.",
                    description = "соус цезарь"
                )
            ),
            method = listOf(
                "Обжарьте или запеките куриное филе и нарежьте ломтиками.",
                "Порвите листья салата в большую миску.",
                "Добавьте курицу, сухарики и тертый пармезан.",
                "Полейте соусом и перемешайте перед подачей."
            ),
            imageUrl = "img_placeholder.jpg"
        )
    )

    fun getCategories(): List<CategoryDto> = categories

    fun getRecipesByCategoryId(categoryId: Int): List<RecipeDto> =
        when (categoryId) {
            0 -> burgerRecipes
            1 -> dessertRecipes
            2 -> pizzaRecipes
            3 -> fishRecipes
            4 -> soupRecipes
            5 -> saladRecipes
            else -> emptyList()
        }
}