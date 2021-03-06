package com.trusov.sociallab.feature_statistics.data.repository

import android.app.Application
import android.os.Environment
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.trusov.sociallab.di.scope.ApplicationScope
import com.trusov.sociallab.feature_statistics.data.source.UStats
import com.trusov.sociallab.feature_statistics.domain.entity.AppScreenTime
import com.trusov.sociallab.feature_statistics.domain.repository.StatisticsRepository
import java.io.File
import java.io.FileWriter
import javax.inject.Inject

@ApplicationScope
class StatisticsRepositoryImpl @Inject constructor(
    private val application: Application,
    private val auth: FirebaseAuth,
    private val firebase: FirebaseFirestore
) : StatisticsRepository {

    override fun getListOfScreenTime(): List<AppScreenTime> {
        val uStats = UStats(application, auth, firebase)
        val listOfScreenTime = uStats.getListOfScreenTime()
        saveScreenTimeToCsv(listOfScreenTime)
        return listOfScreenTime
    }

    override fun getTotalScreenTime(): AppScreenTime {
        val uStats = UStats(application, auth, firebase)
        return uStats.getTotalScreenTime()
    }

    override fun checkUsageStatsPermission(): Boolean {
        val uStats = UStats(application, auth, firebase)
        return uStats.getUsageStatsList().isEmpty()
    }

    private fun saveScreenTimeToCsv(listOfScreenTime: List<AppScreenTime>) {
        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString()
        val dir = File("$root/social_lab")
        val fileName = "screen_time.csv"
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val csvFile = File(dir, fileName)
        if (csvFile.exists()) {
            csvFile.delete()
        } else {
            csvFile.createNewFile()
        }
        Log.d("StatisticsRepoTag", "root: $root")
        if (listOfScreenTime.isNotEmpty()) {
            try {
                val writer = FileWriter(csvFile)
                Log.d("StatisticsRepoTag", "writer after init: $writer")
                val builder = StringBuilder()
                val columnNames ="Application,Hours,Minutes,Seconds\n"
                builder.append(columnNames)
                for (app in listOfScreenTime) {
                    builder.append("${app.appName},${app.hours},${app.minutes},${app.seconds}\n")
                }
                writer.write(builder.toString())
                Log.d("StatisticsRepoTag", "writer after writing: $writer")
                writer.close()
            } catch (e: Exception) {
                Log.d("StatisticsRepoTag", "exception: ${e.message}")
                e.printStackTrace()
            }
        }
    }

}