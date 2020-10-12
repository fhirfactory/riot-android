package im.vector.directory.people

import android.os.Bundle
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import im.vector.R
import im.vector.directory.BaseDirectoryFragment
import im.vector.directory.people.detail.PeopleDetailActivity
import im.vector.directory.people.model.DirectoryPeople
import kotlinx.android.synthetic.main.fragment_directory_people.*
import org.matrix.androidsdk.data.Room

class DirectoryPeopleFragment : BaseDirectoryFragment(), PeopleClickListener {
    private lateinit var peopleDirectoryAdapter: PeopleDirectoryAdapter

    override fun onFilter(pattern: String?, listener: OnFilterListener?) {
        TODO("Not yet implemented")
    }

    override fun onResetFilter() {
        TODO("Not yet implemented")
    }


    override fun getRooms(): MutableList<Room> {
        TODO("Not yet implemented")
    }

    override fun onFavorite(enable: Boolean) {
        //Temporary
        setHeader(header, if (enable) R.string.total_number_of_favourite_people else R.string.total_number_of_people, if (enable) 4 else 10)
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_directory_people
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        peopleDirectoryAdapter = PeopleDirectoryAdapter(requireContext(), this)
        peopleRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        peopleRecyclerview.adapter = peopleDirectoryAdapter

        //test data
        val testPeopleData = mutableListOf<DirectoryPeople>()
        testPeopleData.add(DirectoryPeople("1", "Liam", "Registrar", null, "Emergency Department", "Hospital Department"))
        testPeopleData.add(DirectoryPeople("2", "Noah", "Ward Nurse", null, "Emergency Department", "Hospital Department"))
        testPeopleData.add(DirectoryPeople("3", "William", "Nursing Team Leader", null, "Emergency Department", "Hospital Department"))
        testPeopleData.add(DirectoryPeople("4", "Oliver", "Emergency Admitting Officer", null, "Emergency Department", "Hospital Department"))
        testPeopleData.add(DirectoryPeople("5", "James", "Consultant", null, "Emergency Department", "Hospital Department"))

        peopleDirectoryAdapter.setData(testPeopleData)
        setHeader(header, R.string.total_number_of_people, testPeopleData.size)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.ic_action_advanced_search)?.isVisible = false
    }

    override fun onPeopleClick(directoryPeople: DirectoryPeople) {
        startActivity(PeopleDetailActivity.intent(requireContext(), directoryPeople, true))
    }

    override fun onPeopleFavorite(directoryPeople: DirectoryPeople) {

    }
}