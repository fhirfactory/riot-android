package im.vector.directory.role.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import im.vector.Matrix
import im.vector.R
import im.vector.directory.role.model.DummyRole
import im.vector.util.VectorUtils
import im.vector.view.VectorCircularImageView
import kotlinx.android.synthetic.main.item_role_detail_category1.view.heading
import kotlinx.android.synthetic.main.item_role_detail_category1.view.officialName
import kotlinx.android.synthetic.main.item_role_detail_category1.view.secondaryName
import kotlinx.android.synthetic.main.item_role_detail_category2.view.*
import org.matrix.androidsdk.MXSession


class RolesDetailAdapter(val context: Context) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val roles = mutableListOf<DummyRole>()
    private val TYPE_ROLE = 1
    private val TYPE_ORGANISATION_UNIT = 2
    private val TYPE_TEAM = 3
    private val TYPE_SPECIALITY = 4
    private val TYPE_LOCATION = 5
    private val TYPE_PRACTITIONER_IN_ROLE = 6

    var mSession: MXSession? = null

    init {
        mSession = Matrix.getInstance(context).defaultSession
    }

    inner class RoleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var heading: TextView? = null
        var officialName: TextView? = null
        var secondaryName: TextView? = null

        init {
            heading = itemView.heading
            officialName = itemView.officialName
            secondaryName = itemView.secondaryName
        }

        fun bind(context: Context, session: MXSession?, role: DummyRole) {
            officialName?.text = role.officialName
            secondaryName?.text = role.secondaryName
            //heading.text
        }
    }

    inner class PractitionerInRoleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var avatar: VectorCircularImageView? = null
        var heading: TextView? = null
        var officialName: TextView? = null
        var secondaryName: TextView? = null
        var callIcon: ImageView? = null
        var chatIcon: ImageView? = null
        var videoCallIcon: ImageView? = null

        init {
            avatar = itemView.avatar
            heading = itemView.heading
            officialName = itemView.officialName
            secondaryName = itemView.secondaryName
            callIcon = itemView.callIcon
            chatIcon = itemView.chatIcon
            videoCallIcon = itemView.videoCallIcon
        }

        fun bind(context: Context, session: MXSession?, role: DummyRole) {
            VectorUtils.loadRoomAvatar(context, session, avatar, role)
            officialName?.text = role.officialName
            secondaryName?.text = role.secondaryName
            //heading.text
            callIcon?.setOnClickListener { }
            chatIcon?.setOnClickListener { }
            videoCallIcon?.setOnClickListener { }
        }
    }

    fun setData(roles: MutableList<DummyRole>) {
        this.roles.clear()
        this.roles.addAll(roles)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {
        // create a new view
        return when (viewType) {
            TYPE_ROLE, TYPE_ORGANISATION_UNIT, TYPE_TEAM, TYPE_SPECIALITY, TYPE_LOCATION -> RoleViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_role_detail_category1, parent, false))
            TYPE_PRACTITIONER_IN_ROLE -> PractitionerInRoleViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_role_detail_category2, parent, false))
            else -> RoleViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_role_detail_category1, parent, false))
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (roles[position].type) {
            TYPE_ROLE, TYPE_ORGANISATION_UNIT, TYPE_TEAM, TYPE_SPECIALITY, TYPE_LOCATION -> (holder as RoleViewHolder).bind(context, mSession, roles[position])
            TYPE_PRACTITIONER_IN_ROLE -> (holder as PractitionerInRoleViewHolder).bind(context, mSession, roles[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (roles[position].type) {
            1 -> TYPE_ROLE
            2 -> TYPE_ORGANISATION_UNIT
            3 -> TYPE_TEAM
            4 -> TYPE_SPECIALITY
            5 -> TYPE_LOCATION
            6 -> TYPE_PRACTITIONER_IN_ROLE
            else -> TYPE_ROLE
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = roles.size
}