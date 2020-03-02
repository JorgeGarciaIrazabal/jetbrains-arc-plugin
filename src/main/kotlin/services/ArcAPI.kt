package services

import ArcProject
import User
import khttp.post
import org.json.JSONArray
import org.json.JSONObject
import org.json.simple.parser.JSONParser
import java.io.FileNotFoundException
import java.io.FileReader
import java.nio.file.Path
import java.nio.file.Paths
import org.json.simple.JSONObject as JsonObject

object ArcAPI {
    private var arcToken: String = ""
    private var arcHost: String = ""
    private var arcPath: Path = Path.of("")
    private var arcEnabled: Boolean = true
    private var users: MutableList<User> = mutableListOf()
    private var projects: MutableList<ArcProject> = mutableListOf()

    init {
        try {
            val home = Paths.get(System.getProperty("user.home"))
            arcPath = home.resolve(".arcrc")
            val reader = FileReader(arcPath.toAbsolutePath().toString())
            val jsonParser = JSONParser()
            val arcJson: JsonObject = jsonParser.parse(reader) as JsonObject
            val hosts = arcJson["hosts"] as JsonObject
            arcHost = hosts.keys.iterator().next() as String
            arcToken = (hosts[arcHost] as JsonObject)["token"] as String
        } catch (e: FileNotFoundException) {
            arcEnabled = false
        }
    }

    fun getUsers(): List<User> {
        if (users.isEmpty()) {
            if (!arcEnabled) {
                users.add(User(name = "Jorge Garcia Irazabal", username = "jirazabal", id = 1))
                users.add(User(name = "Appachu", username = "cappachu", id = 2))
                users.add(User(name = "Marius", username = "mvanniekerk", id = 3))
                users.add(User(name = "Mariusq", username = "mvanniekerkq", id = 3))
                users.add(User(name = "Marius1", username = "mvanniekerk1", id = 3))
                users.add(User(name = "Marius2", username = "mvanniekerk2", id = 3))
                return users
            }

            val projectJsonList = getAll("user.search")
            users = projectJsonList.map {
                val fields = it.getJSONObject("fields")
                User(name = fields.getString("realName"), username = fields.getString("username"), id = it.getInt("id"))
            }.toMutableList()
        }
        return users
    }

    fun getProjects(): List<ArcProject> {
        if (projects.isEmpty()) {
            if (!arcEnabled) {
                projects.add(ArcProject(name = "data-tooling", slug = "data-tooling", id = 1))
                return projects
            }
            val projectJsonList = getAll("project.search")
            projects = projectJsonList.map {
                val fields = it.getJSONObject("fields")
                ArcProject(name = fields.getString("name"), slug = fields.getString("slug"), id = it.getInt("id"))
            }.toMutableList()
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