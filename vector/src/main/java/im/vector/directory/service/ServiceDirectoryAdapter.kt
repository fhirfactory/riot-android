package im.vector.directory.service

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import im.vector.Matrix
import im.vector.R
import im.vector.util.VectorUtils
import im.vector.view.VectorCircularImageView
import kotlinx.android.synthetic.main.item_directory_service.view.*
import org.matrix.androidsdk.MXSession


class ServiceDirectoryAdapter(val context: Context, private val onClickListener: ServiceClickListener) :
        RecyclerView.Adapter<RoleViewHolder>(), OnDataSetChange {
    private val services = mutableListOf<DummyService>()

    fun setData(roles: MutableList<DummyService>) {
        this.services.clear()
        this.services.addAll(roles)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RoleViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_directory_service, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return RoleViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RoleViewHolder, position: Int) {
        holder.bind(context, services[position], onClickListener)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = services.size

    override fun onDataChange(position: Int) {
        notifyItemChanged(position)
    }
}

interface RoleClickListener {
    fun onRoleClick(role: DummyService)
}

interface OnDataSetChange {
    fun onDataChange(position: Int)
}

class RoleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var officialName: TextView? = null
    var locationCode: TextView? = null
    var locationDetail: TextView? = null
    var favoriteIcon: ImageView? = null

    init {
        officialName = itemView.officialName
        locationCode = itemView.locationCode
        locationDetail = itemView.locationDetail
        favoriteIcon = itemView.favoriteIcon
    }

    fun bind(context: Context, service: DummyService, onClickListener: ServiceClickListener?) {
        favoriteIcon?.setImageResource(if(service.isFavorite) R.drawable.filled_star else R.drawable.outline_star)
        itemView.setOnClickListener {
            onClickListener?.onServiceClick(service)
        }
    }
}

interface ServiceClickListener {
    fun onServiceClick(service: DummyService)
}