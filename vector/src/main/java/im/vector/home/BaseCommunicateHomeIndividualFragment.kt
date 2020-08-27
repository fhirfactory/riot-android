package im.vector.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import androidx.core.content.ContextCompat
import im.vector.R
import im.vector.adapters.AbsAdapter
import im.vector.adapters.HomeRoomAdapter
import im.vector.fragments.AbsHomeFragment
import im.vector.ui.themes.ThemeUtils
import kotlinx.android.synthetic.main.fragment_home_individual.*
import org.matrix.androidsdk.data.Room
import java.util.*
import kotlin.collections.ArrayList


abstract class BaseCommunicateHomeIndividualFragment : BaseCommunicateHomeFragment(), UpDateListener {
    private val LOG_TAG = BaseCommunicateHomeIndividualFragment::class.java.simpleName

    var registerListener: RegisterListener? = null
    var onSelectRoomListener: HomeRoomAdapter.OnSelectRoomListener? = null
    var invitationListener: AbsAdapter.RoomInvitationListener? = null
    var moreActionListener: AbsAdapter.MoreRoomActionListener? = null

    val localRooms = ArrayList<Room>()

    override fun getLayoutResId(): Int {
        return R.layout.fragment_home_individual
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sectionView.mHeader.visibility = GONE
        sectionView.mBadge.visibility = GONE
        sectionView.setHideIfEmpty(true)
    }

    override fun onUpdate(rooms: List<Room>?, comparator: Comparator<Room>) {
        Log.d(LOG_TAG, "called" + rooms?.size)
        localRooms.clear()
        try {
            Collections.sort(rooms, comparator)
        } catch (e: Exception) {
            org.matrix.androidsdk.core.Log.e(LOG_TAG, "## sortAndDisplay() failed " + e.message, e)
        }
        rooms?.let {
            localRooms.addAll(rooms)
            if (sectionView != null) {
                sectionView.setRooms(localRooms)
            }
        }
    }

    override fun onFilter(pattern: String?, listener: OnFilterListener?) {
        sectionView.onFilter(pattern, listener)
    }

    override fun onResetFilter() {
        sectionView.onFilter("", null)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        registerListener?.onRegister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        registerListener?.onUnregister(this)
    }

    fun addListener(registerListener: RegisterListener?, selectionListener: HomeRoomAdapter.OnSelectRoomListener?, invitationListener: AbsAdapter.RoomInvitationListener?, moreActionListener: AbsAdapter.MoreRoomActionListener?){
        this.registerListener = registerListener
        this.onSelectRoomListener = selectionListener
        this.invitationListener = invitationListener
        this.moreActionListener = moreActionListener
    }
}

interface UpDateListener {
    fun onUpdate(rooms: List<Room>?, comparator: Comparator<Room>)
    fun onFilter(pattern: String?, listener: AbsHomeFragment.OnFilterListener?)
    fun onResetFilter()
}

interface RegisterListener {
    fun onRegister(listener: UpDateListener)
    fun onUnregister(listener: UpDateListener)
}