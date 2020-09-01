package im.vector.home

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import im.vector.R
import kotlinx.android.synthetic.main.fragment_home_individual.*

class LowPriorityRoomFragment : BaseCommunicateHomeIndividualFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sectionView.setTitle(R.string.total_notification)
        sectionView.setPlaceholders(null, getString(R.string.no_result_placeholder))
        sectionView.setupRoomRecyclerView(LinearLayoutManager(activity, RecyclerView.VERTICAL, false),
                R.layout.adapter_item_room_view, true, onSelectRoomListener, invitationListener, moreActionListener)
        sectionView.setRooms(localRooms)
    }

    override fun getRoomFragmentType(): CommunicateHomeFragment.ROOM_FRAGMENTS {
        return CommunicateHomeFragment.ROOM_FRAGMENTS.LOW_PRIORITY
    }

    override fun onBadgeUpdate(count: Int) {
        communicateTabBadgeUpdateListener?.onBadgeUpdate(count, CommunicateHomeFragment.ROOM_FRAGMENTS.LOW_PRIORITY)
    }
}