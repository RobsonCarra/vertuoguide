package br.com.alura.ceep.ui.coffemachine.presentation.view

class CoffesRepository(private val coffesDao: CoffesDao) {

    fun get() = coffesDao.getAllCoffes()

    fun getSpecicCoffe(id: Long) =
        coffesDao.getById(id)


    fun delete(coffes: Coffes): Boolean {
        val id = coffesDao.deleteCoffe(coffes)
        return id > 0
    }

    fun save(coffes: Coffes): Boolean {
        val id = coffesDao.save(coffes)
        return id > 0
    }


//    private val mnamesDao: CoffesDao
//    private val mquantityDao: CoffesDao
//    val allNames: LiveData<List<Coffes>>
//    val allCapsules: LiveData<List<Coffes>>
//
//    fun insert(coffes: Coffes?) {
//        insertAsyncTask(mnamesDao).execute(coffes)
//    }
//
//    fun insert(coffes: Coffes?) {
//        insertAsyncTask(mquantityDao).execute(coffes)
//    }
//
//    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: CoffesDao) :
//        AsyncTask<Coffes?, Void?, Void?>() {
//        fun execute(coffes: Coffes?) {}
//        protected override fun doInBackground(vararg params: Coffes): Void? {
//            mAsyncTaskDao.insert(params[0])
//            return null
//        }
//    }

//    init {
//        val db = CoffeRoomDataBase.getDatabase(application)
//        mnamesDao = db.coffesDao()
//        mquantityDao = db.coffesDao()
//        allNames = mnamesDao.allNames
//        allCapsules = mquantityDao.allCapsules
//    }
}