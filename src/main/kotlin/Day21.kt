class Day21 : Day() {
    override val label: String get() = "21"

    data class Food(val ingredients: List<String>, val allergens: List<String>)

    private val foodRegex = """(.+) \(contains (.+)\)""".toRegex()

    private val foods by lazy { input
        .lineSequence()
        .map {
            val (ingredients, allergens) = foodRegex.find(it)?.destructured ?: throw Exception()
            Food(ingredients.split(' '), allergens.split(", ")) }
        .toList() }

    private val allergens by lazy { foods.flatMap { it.allergens }.distinct().toList() }

    private val ingredients by lazy { foods.flatMap { it.ingredients }.distinct().toList() }

    private val inertIngredients by lazy { allergens
        .flatMap { allergen ->
            val foodsWhichContain = foods.filter { it.allergens.contains(allergen) }.toList()
            foodsWhichContain
                .flatMap { it.ingredients }
                .distinct()
                .filter { ingredient -> foodsWhichContain.all { it.ingredients.contains(ingredient) } }
        }
        .distinct()
        .toHashSet() }

    private val safeIngredients by lazy { ingredients
        .filterNot { inertIngredients.contains(it) }
        .distinct()
        .toHashSet() }

    override fun taskZeroLogic(): String =
        foods.flatMap { it.ingredients }.filter { safeIngredients.contains(it) }.count().toString()

    override fun taskOneLogic(): String {
        val excludedIngredients = safeIngredients.toHashSet()
        val excludedAllergens = HashSet<String>()
        val allergensToIngredients = mutableListOf<Pair<String,String>>()
        for(i in allergens.indices) {
            val nextExclude = allergens
                .filterNot { excludedAllergens.contains(it) }
                .map { allergen ->
                    val foodsWhichContain = foods.filter { it.allergens.contains(allergen) }.toList()
                    allergen to foodsWhichContain
                        .flatMap { it.ingredients }
                        .distinct()
                        .filter { ingredient -> foodsWhichContain.all { it.ingredients.contains(ingredient) } }
                        .filterNot { excludedIngredients.contains(it) }
                        .toList()
                }
                .firstOrNull { it.second.count() == 1 }
            if (nextExclude != null) {
                allergensToIngredients.add(nextExclude.first to nextExclude.second[0])
                excludedIngredients.add(nextExclude.second[0])
                excludedAllergens.add(nextExclude.first)
            }
            else throw Exception()
        }
        return allergensToIngredients.asSequence().sortedBy { it.first }.map { it.second }.joinToString(",")
    }
}