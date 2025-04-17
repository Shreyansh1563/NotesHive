package com.example.noteshive.viewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.example.noteshive.models.BranchModel
import com.example.noteshive.models.NotesModel
import com.example.noteshive.models.SubjectModel
import com.example.noteshive.models.YearModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class OptionsViewModel : ViewModel() {
    private val _db = FirebaseFirestore.getInstance()

    private val _branchCollection = _db.collection("branches")
    private var _yearCollection: CollectionReference? = null
    private var _subjectCollection: Query? = null
    private var _notesCollection: Query? = null

    private val _branchData =  mutableStateListOf<BranchModel>()
    private val _yearData = mutableStateListOf<YearModel>()
    private val _subjectData = mutableStateListOf<SubjectModel>()
    private val _notesData = mutableStateListOf<NotesModel>()

    private var selectedBranch: BranchModel? = null
    private var selectedYear: YearModel? = null
    var selectedSubject: SubjectModel? = null


    val branchData: List<BranchModel> = _branchData
    val yearData: List<YearModel> = _yearData
    val subjectData: List<SubjectModel> = _subjectData
    val notesData : List<NotesModel> = _notesData



    init{
        loadBranchData()
    }

    private fun loadBranchData(){
        _branchCollection.addSnapshotListener{value, error->
            if(error == null){
                val dbData = value?.toObjects(BranchModel:: class.java)
                _branchData.addAll(dbData!!)
            }
            else {
                Log.d("data not Loading", error.toString())
            }
        }
    }

    private fun loadYearData(){

        _yearCollection = _db.collection("/branches/${selectedBranch!!.id}/years")
        _yearCollection!!.addSnapshotListener { value, error ->
            if (error == null) {
                val dbData = value?.toObjects(YearModel::class.java)
                _yearData.addAll(dbData!!)
            }
        }
    }

    private fun loadSubjectData(){
        _subjectCollection = _db.collection("subjects").whereIn("id", selectedYear!!.subjectsId)
        _subjectCollection!!.addSnapshotListener{value, error->
            if(error == null){
                val dbData = value?.toObjects(SubjectModel:: class.java)
                _subjectData.addAll(dbData!!)
            }
        }
    }

    private fun loadNotesData(){
        Log.d("loading notes data", selectedSubject!!.id)
        _notesCollection = _db.collection("/subjects/${selectedSubject!!.id}/notes")
        _notesCollection!!.addSnapshotListener{value, error->
            if(error == null){
                val  dbData = value?.toObjects(NotesModel:: class.java)
                _notesData.clear()
                _notesData.addAll(dbData!!)
            }
        }
    }

    // this function renders pdf in pdf drive
    fun openPdfWithIntent(context: Context, pdfUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(pdfUrl.toUri(), "application/pdf")
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        context.startActivity(intent)
    }

    fun branchSelected(it: BranchModel){
        selectedBranch = it
        _yearData.clear()
        loadYearData()
    }

    fun yearSelected(it: YearModel){
        selectedYear = it
        _subjectData.clear()
        loadSubjectData()
    }

    fun subjectSelected(it: SubjectModel){
        selectedSubject = it
        loadNotesData()
    }
}