import java.lang.Exception
import java.util.*
import kotlin.collections.HashSet
import kotlin.math.sqrt

class Day20 : Day() {
    override val label: String get() = "20"

    class TileContent(var content: List<String>) {
        fun rotateBy(steps: Int) {
            when (steps % 4) {
                1 -> { content = (content[0].indices).map { content.reversed().map { s -> s[it] }.joinToString("") }.toList() }
                2 -> { content = content.reversed().map { it.reversed() }.toList() }
                3 -> { content = ((content[0].length - 1) downTo 0).map { content.map { s -> s[it] }.joinToString("") }.toList() }
                else -> {}
            }
        }

        fun flipHorizontally() {
            content = content.reversed().toList()
        }

        private val pattern = listOf(0 to 1, 1 to 2,
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
    }

    class Tile(val id: Int, var edge0: String, var edge1: String, var edge2: String, var edge3: String, val content: TileContent) {
        val edge0rev: String
            get() = edge0.reversed()

        val edge1rev: String
            get() = edge1.reversed()

        val edge2rev: String
            get() = edge2.reversed()

        val edge3rev: String
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

        fun rotateBy(steps: Int) {
            val i = steps % 4
            when (i) {
                1 -> {
                    val e0 = edge3; val e1 = edge0; val e2 = edge1; val e3 = edge2
                    edge0 = e0; edge1 = e1; edge2 = e2; edge3 = e3
                }
                2 -> {
                    val e0 = edge2; val e1 = edge3; val e2 = edge0; val e3 = edge1
                    edge0 = e0; edge1 = e1; edge2 = e2; edge3 = e3
                }
                3 -> {
                    val e0 = edge1; val e1 = edge2; val e2 = edge3; val e3 = edge0
                    edge0 = e0; edge1 = e1; edge2 = e2; edge3 = e3
                }
                else -> {}
            }
            content.rotateBy(i)
        }

        fun flipHorizontally()
        {
            val e0 = edge2rev; val e1 = edge1rev; val e2 = edge0rev; val e3 = edge3rev
            edge0 = e0; edge1 = e1; edge2 = e2; edge3 = e3
            content.flipHorizontally()
        }

        fun fitsTo(up: Tile?, right: Tile?, down: Tile?, left: Tile?): Boolean {
            fun fitsToInner(): Boolean =
                (up == null || up.edge2 == edge0rev) &&
                        (right == null || right.edge3 == edge1rev) &&
                        (down == null || down.edge0 == edge2rev) &&
                        (left == null || left.edge1 == edge3rev)
            for(i in (0..3)) {
                if (fitsToInner()) return true
                rotateBy(1)
            }
            flipHorizontally()
            for(i in (0..3)) {
                if (fitsToInner()) return true
                rotateBy(1)
            }
            flipHorizontally()
            return false
        }
    }

    class TileHolder {
        var x: Int = 0
        var y: Int = 0
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
                val edge0 = tileLines.first()
                val edge2 = tileLines.last().reversed()
                val edge1 = tileLines.asSequence().map { it.last() }.joinToString("")
                val edge3 = tileLines.reversed().asSequence().map { it.first() }.joinToString("")
                val content = TileContent(tileLines
                    .drop(1)
                    .dropLast(1)
                    .map { line -> line.substring(1 until (line.length - 1)) })
                Tile(id, edge0, edge1, edge2, edge3, content)
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
                    mapInner[y][x].x = x
                    mapInner[y][x].y = y
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

        fun arrangeTiles() {
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
        }

        fun mergeContent(): TileContent {
            val content = map
                .flatMap { arr -> (0 until (arr[0].tile?.content?.content?.size ?: 0))
                    .map { arr.map { t -> t.tile?.content?.content!![it] }.joinToString("") } }
                .toList()

            return TileContent(content)
        }
    }

    private val biggerPicture by lazy { BiggerPicture(input) }

    override fun taskZeroLogic(): String = biggerPicture.cornerIdsMultiplication().toString()

    override fun taskOneLogic(): String {
        biggerPicture.arrangeTiles()
        val mergeContent = biggerPicture.mergeContent()

        for(i in (0..3)) {
            val (any, count) = mergeContent.waterRoughness()
            if (any) return count.toString()
            mergeContent.rotateBy(1)
        }
        mergeContent.flipHorizontally()
        for(i in (0..3)) {
            val (any, count) = mergeContent.waterRoughness()
            if (any) return count.toString()
            mergeContent.rotateBy(1)
            val (any1, count1) = mergeContent.waterRoughness()
            if (any1) return count1.toString()
        }
        return noSolutionFound
    }
}