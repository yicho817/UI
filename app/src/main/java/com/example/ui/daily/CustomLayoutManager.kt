import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomLayoutManager(context: Context) : LinearLayoutManager(context, HORIZONTAL, false) {

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        // 調用父類的onLayoutChildren方法，將所有項目加載進來
        super.onLayoutChildren(recycler, state)

        // 單個項目的寬度
        val itemWidth = width / 3

        // 總的可見項目數
        val visibleItemCount = width / itemWidth

        // 起始的偏移量
        val startOffset = (width - itemWidth) / 2

        // 將所有項目平移，實現疊起來的效果
        for (i in 0 until visibleItemCount) {
            getChildAt(i)?.let { child ->
                val offset = startOffset - i * itemWidth
                layoutDecorated(child, child.left + offset, child.top, child.right + offset, child.bottom)
            }
        }
    }
}
