import java.lang.Exception
import java.util.*
import kotlin.collections.HashSet
import kotlin.math.sqrt

class Day20 : Day() {
    override val label: String get() = "20"

    class TileContent(var content: List<String>) {

        val mergeAbleContent: List<String>
            get() = content.drop(1).dropLast(1).map { it.substring(1 until (it.length - 1)) }

        fun rotateBy(steps: Int) {
            when (steps % 4) {
                1 -> { content = (content[0].indices).map { content.reversed().map { s -> s[it] }.joinToString("") }.toList() }
                2 -> { content = content.reversed().map { it.reversed() }.toList() }
                3 -> { content = ((content[0].length - 1) downTo 0).map { content.map { s -> s[it] }.joinToString("") }.toList() }
                else -> {}
            }
        }

        private fun flipHorizontally() { content = content.reversed().toList() }

        private val pattern = listOf(
            0 to 1, 1 to 2,
            4 to 2, 5 to 1, 6 to 1, 7 to 2,
            10 to 2, 11 to 1, 12 to 1, 13 to 2,
            16 to 2, 17 to 1, 18 to 0, 18 to 1, 19 to 1)

        fun waterRoughness(): Pair<Boolean, Int> {
            val allHashtags = content
                .flatMapIndexed { y, it -> it
                    .mapIndexed { x, c -> x to c }
                    .filter { p -> p.second == '#' }
                    .map { p -> p.first to y } }
                .toHashSet()

            val diff = HashSet<Pair<Int, Int>>()

            for (y in 0 until (content.size - 3))
                for (x in 0 until (content[0].length - 20)) {
                    val possibleMonster = pattern.map { (it.first + x) to (it.second + y) }.toHashSet()
                    if(possibleMonster.all { allHashtags.contains(it) })
                        possibleMonster.forEach{diff.add(it)}
                }
            return diff.any() to allHashtags.subtract(diff).count()
        }

        fun allRotationsAndFlips(): Sequence<TileContent> {
            val thisTileContent = this
            return sequence {
                for(i in (0..3)) {
                    yield(thisTileContent)
                    rotateBy(1)
                }
                flipHorizontally()
                for(i in (0..2)) {
                    yield(thisTileContent)
                    rotateBy(1)
                }
                yield(thisTileContent)
            }
        }
    }

    class Tile(val id: Int, val content: TileContent) {

        private val edge0: String
            get() = content.content[0]
        private val edge1: String
            get() = content.content.map { it.last() }.joinToString("")
        private val edge2: String
            get() = content.content.last().reversed()
        private val edge3: String
            get() = content.content.reversed().map { it.first() }.joinToString("")

        private val edge0rev: String
            get() = edge0.reversed()
        private val edge1rev: String
            get() = edge1.reversed()
        private val edge2rev: String
            get() = edge2.reversed()
        private val edge3rev: String
            get() = edge3.reversed()

        fun countOfBorderEdges(tileSet: List<Tile>): Int {
            val borderEdges = sequence {
                yield(edge0 to edge0rev)
                yield(edge1 to edge1rev)
                yield(edge2 to edge2rev)
                yield(edge3 to edge3rev) }
                .map { p ->
                    val (edge, edgeRev) = p
                    tileSet
                        .asSequence()
                        .filterNot { it == this }
                        .none { it.edge0 == edge || it.edge0 == edgeRev ||
                                it.edge1 == edge || it.edge1 == edgeRev ||
                                it.edge2 == edge || it.edge2 == edgeRev ||
                                it.edge3 == edge || it.edge3 == edgeRev } }
                .toList()
            return if(borderEdges[3] && borderEdges[0] && borderEdges[1].not() && borderEdges[2].not()) 0
            else if(borderEdges[0] && borderEdges[1] && borderEdges[2].not() && borderEdges[3].not()) 3
            else if(borderEdges[1] && borderEdges[2] && borderEdges[3].not() && borderEdges[0].not()) 2
            else if(borderEdges[2] && borderEdges[3] && borderEdges[0].not() && borderEdges[1].not()) 1
            else -1
        }

        fun rotateBy(steps: Int) = content.rotateBy(steps)

        fun fitsTo(up: Tile?, right: Tile?, down: Tile?, left: Tile?): Boolean =
            content.allRotationsAndFlips().firstOrNull {
                (up == null || up.edge2 == edge0rev) &&
                    (right == null || right.edge3 == edge1rev) &&
                    (down == null || down.edge0 == edge2rev) &&
                    (left == null || left.edge1 == edge3rev) } != null
    }

    class TileHolder {
        var tile: Tile? = null
        var up: TileHolder? = null
        var down: TileHolder? = null
        var left: TileHolder? = null
        var right: TileHolder? = null

        fun checkAndSetSingleFit(unArrangedTiles: HashSet<Tile>): Boolean {
            val fittingTiles = unArrangedTiles
                .filter { it.fitsTo(up?.tile, right?.tile, down?.tile, left?.tile) }
                .toList()
            if (fittingTiles.count() == 1) {
                tile = fittingTiles[0]
                unArrangedTiles.remove(fittingTiles[0])
                return true
            }
            return false
        }

        fun appendUnassignedNeighbors(queue: LinkedList<TileHolder>) {
            fun appendUnassignedNeighborsInner(tileHolder: TileHolder?) {
                if (tileHolder != null && tileHolder.tile == null && !queue.contains(tileHolder))
                    queue.addLast(tileHolder)
            }
            appendUnassignedNeighborsInner(up)
            appendUnassignedNeighborsInner(right)
            appendUnassignedNeighborsInner(down)
            appendUnassignedNeighborsInner(left)
        }
    }

    class BiggerPicture(val input: String) {
        private val idRegex = """^Tile ([0-9]+):${'$'}""".toRegex()

        private val tiles by lazy { input
            .splitToSequence("\r\n\r\n")
            .filterNot { it.isBlank() }
            .map { text ->
                val (idText) = idRegex.find(text.lineSequence().first())?.destructured ?: throw Exception()
                val id = idText.toIntOrNull() ?: throw Exception()
                val tileLines = text.lineSequence().drop(1).filterNot { it.isBlank() }.toList()
                val content = TileContent(tileLines)
                Tile(id, content)
            }
            .toList() }

        private val cornerTiles by lazy { tiles
            .asSequence()
            .filter { it.countOfBorderEdges(tiles) >= 0 }
            .toList() }

        private val mapWidth: Int by lazy { sqrt(tiles.size.toDouble()).toInt() }

        private val map by lazy {
            val mapInner = Array(mapWidth) { Array(mapWidth) { TileHolder() } }
            for (y in mapInner.indices)
                for (x in mapInner[y].indices) {
                    if (x > 0)                    mapInner[y][x].left  = mapInner[y    ][x - 1]
                    if (x < mapInner[y].size - 1) mapInner[y][x].right = mapInner[y    ][x + 1]
                    if (y > 0)                    mapInner[y][x].up    = mapInner[y - 1][x    ]
                    if (y < mapInner   .size - 1) mapInner[y][x].down  = mapInner[y + 1][x    ]
                }
            mapInner
        }

        private fun initializeUpperLeftCorner() {
            cornerTiles[0].rotateBy(cornerTiles[0].countOfBorderEdges(tiles))
            map[0][0].tile = cornerTiles[0]
        }

        fun cornerIdsMultiplication(): Long = cornerTiles.fold(1L) { acc, next -> acc * next.id.toLong() }

        fun arrangeTiles(): BiggerPicture {
            initializeUpperLeftCorner()
            val unArrangedTiles = tiles.filterNot { it == map[0][0].tile }.toHashSet()
            val queue = LinkedList<TileHolder>()
            queue.addLast(map[0][1])
            queue.addLast(map[1][0])
            while (queue.count() > 0) {
                val current = queue.pop()
                if (current.checkAndSetSingleFit(unArrangedTiles)) current.appendUnassignedNeighbors(queue)
                else queue.addLast(current)
            }
            return this
        }

        fun mergeContent(): TileContent {
            val content = map
                .flatMap { arr -> (0 until (arr[0].tile?.content?.mergeAbleContent?.size ?: 0))
                    .map { arr.map { t -> t.tile?.content?.mergeAbleContent!![it] }.joinToString("") } }
                .toList()

            return TileContent(content)
        }
    }

    private val biggerPicture by lazy { BiggerPicture(input) }

    override fun taskZeroLogic(): String = biggerPicture.cornerIdsMultiplication().toString()

    override fun taskOneLogic(): String = biggerPicture
        .arrangeTiles()
        .mergeContent()
        .allRotationsAndFlips()
        .map { it.waterRoughness() }
        .filter { it.first }
        .map { it.second }
        .firstOrNull()
        ?.toString()
        ?: noSolutionFound
}