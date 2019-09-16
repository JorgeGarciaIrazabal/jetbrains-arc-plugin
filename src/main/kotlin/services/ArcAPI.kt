package services

import Project
import User
import khttp.post
import org.json.JSONArray
import org.json.JSONObject
import org.json.simple.parser.JSONParser
import java.io.FileReader
import java.nio.file.Path
import java.nio.file.Paths
import org.json.simple.JSONObject as JsonObject

object ArcAPI {
    private var arcToken: String
    private var arcHost: String
    private var arcPath: Path
    private var users: List<User> = emptyList()
    private var projects: List<Project> = emptyList()

    init {
        val home = Paths.get(System.getProperty("user.home"))
        arcPath = home.resolve(".arcrc")
        val reader = FileReader(arcPath.toAbsolutePath().toString())
        val jsonParser = JSONParser()
        val arcJson: JsonObject = jsonParser.parse(reader) as JsonObject
        val hosts = arcJson["hosts"] as JsonObject
        arcHost = hosts.keys.iterator().next() as String
        arcToken = (hosts[arcHost] as JsonObject)["token"] as String
    }

    fun getUsers(): List<User> {
        if (users.isEmpty()) {
            val projectJsonList = getAll("user.search")
            users = projectJsonList.map {
                val fields = it.getJSONObject("fields")
                User(name = fields.getString("realName"), username = fields.getString("username"), id = it.getInt("id"))
            }
        }
        return users
    }

    fun getProjects(): List<Project> {
        if (projects.isEmpty()) {
            val projectJsonList = getAll("project.search")
            projects = projectJsonList.map {
                val fields = it.getJSONObject("fields")
                Project(name = fields.getString("name"), slug = fields.getString("slug"), id = it.getInt("id"))
            }
        }
        return projects
    }

    private fun getAll(url: String): MutableList<JSONObject> {
        var after: String? = null
        val dataJson = mutableListOf<JSONObject>()
        do {
            val myJson = post(url, after)

            val result = myJson.getJSONObject("result")
            val jsonAfter = result.getJSONObject("cursor").get("after")

            after = if (jsonAfter.equals(null)) null else jsonAfter as String?

            val data = result.get("data") as JSONArray
            data.forEach() {
                dataJson.add(it as JSONObject)
            }
        } while (after != null)
        return dataJson
    }

    private fun post(url: String, after: String?): JSONObject {
        val params = mutableMapOf(
            "api.token" to arcToken
        )
        if (after != null) params["after"] = after

        val myJson = post(
            "${arcHost}${url}", params = params, timeout = 3.0, headers = mapOf(
                "Content-Type" to "application/x-www-form-urlencoded"
            )
        ).jsonObject
        return myJson
    }
}