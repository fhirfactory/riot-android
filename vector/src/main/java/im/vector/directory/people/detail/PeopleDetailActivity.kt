package im.vector.directory.people.detail

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import im.vector.Matrix
import im.vector.R
import im.vector.activity.MXCActionBarActivity
import im.vector.directory.people.model.DirectoryPeople
import im.vector.directory.role.RoleClickListener
import im.vector.directory.role.detail.RoleDetailActivity
import im.vector.directory.role.model.*
import im.vector.util.VectorUtils
import kotlinx.android.synthetic.main.activity_people_detail.*
import kotlinx.android.synthetic.main.activity_people_detail.peopleRecyclerview
import kotlinx.android.synthetic.main.fragment_directory_people.*

class PeopleDetailActivity : MXCActionBarActivity(), FragmentManager.OnBackStackChangedListener, RoleClickListener {
    private lateinit var peopleDetailAdapter: PeopleDetailAdapter

    override fun getLayoutRes(): Int = R.layout.activity_people_detail

    override fun initUiAndData() {
        configureToolbar()
        mSession = Matrix.getInstance(this).defaultSession

        val people = intent.getParcelableExtra<DirectoryPeople>(PEOPLE_EXTRA)
        val roleClickable = intent.getBooleanExtra(ROLE_CLICKABLE, false)
        supportActionBar?.let {
            it.title = people.officialName
        }
        VectorUtils.loadRoomAvatar(this, session, avatar, people)

        jobTitle.text = people.jobTitle
        organisation.text = people.organisations
        businessUnit.text = people.businessUnits

        peopleDetailAdapter = PeopleDetailAdapter(this, if (roleClickable) this else null)
        peopleRecyclerview.layoutManager = LinearLayoutManager(this)
        peopleRecyclerview.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        peopleRecyclerview.adapter = peopleDetailAdapter
        peopleDetailAdapter.setData(people)

        val testRoleData = mutableListOf<DummyRole>()
        testRoleData.add(DummyRole("1", "ED Acute SRMO", "Emergency Department  Acute Senior Resident Medical Officer Medical Officer", null, "ED {Emergency Department}", arrayListOf(Role("1", "Senior Resident Medical Officer", "Doctor")),
                arrayListOf(Speciality("1", "Emergency")), arrayListOf(DummyLocation("1", "CH {Canberra Hospital}")), arrayListOf(Team("1", "Emergency Department Acute"))))

        testRoleData.add(DummyRole("1", "ED Acute RMO", "Emergency Department  Acute Resident Medical Officer", null, "ED {Emergency Department}", arrayListOf(Role("1", "Resident", "Doctor")),
                arrayListOf(Speciality("1", "Emergency")), arrayListOf(DummyLocation("1", "CH {Canberra Hospital}")), arrayListOf(Team("1", "Emergency Department Acute"))))

        testRoleData.add(DummyRole("1", "ED Acute Intern", "Emergency Department  Acute Intern", null, "ED {Emergency Department}", arrayListOf(Role("1", "Intern", "Doctor")),
                arrayListOf(Speciality("1", "Emergency")), arrayListOf(DummyLocation("1", "CH {Canberra Hospital}")), arrayListOf(Team("1", "Emergency Department Acute"))))

        testRoleData.add(DummyRole("1", "ED Acute Consultant", "Emergency Department  Acute Consultant", null, "ED {Emergency Department}", arrayListOf(Role("1", "Consultant", "Doctor")),
                arrayListOf(Speciality("1", "Emergency")), arrayListOf(DummyLocation("1", "CH {Canberra Hospital}")), arrayListOf(Team("1", "Emergency Department Acute"))))

        testRoleData.add(DummyRole("1", "ED Acute East Nurse", "Emergency Department  Acute East Nurse", null, "ED {Emergency Department}", arrayListOf(Role("1", "Emergency Department Nurse", "Nursing and Midwifery")),
                arrayListOf(Speciality("1", "Emergency")), arrayListOf(DummyLocation("1", "CH {Canberra Hospital}")), arrayListOf(Team("1", "Emergency Department Acute"))))

        peopleDetailAdapter.setData(testRoleData)


        callIcon.setOnClickListener { }
        chatIcon.setOnClickListener { }
        videoCallIcon.setOnClickListener { }
    }

    override fun onDestroy() {
        supportFragmentManager.removeOnBackStackChangedListener(this)
        super.onDestroy()
    }

    override fun onBackStackChanged() {
        if (0 == supportFragmentManager.backStackEntryCount) {
            supportActionBar?.title = getString(getTitleRes())
        }
    }

    companion object {
        private const val PEOPLE_EXTRA = "PEOPLE_EXTRA"
        private const val ROLE_CLICKABLE = "ROLE_CLICKABLE"
        fun intent(context: Context, directoryPeople: DirectoryPeople, roleClickable: Boolean = false): Intent {
            return Intent(context, PeopleDetailActivity::class.java).also {
                it.putExtra(PEOPLE_EXTRA, directoryPeople)
                it.putExtra(ROLE_CLICKABLE, roleClickable)
            }
        }
    }

    override fun onRoleClick(role: DummyRole, forRemove: Boolean) {
        startActivity(RoleDetailActivity.intent(this, role, false))
    }
}