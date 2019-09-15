package services

import org.junit.jupiter.api.Assertions.*

internal class ArcAPITest() {
    private var arcApi: ArcAPI = ArcAPI()

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
        this.arcApi = ArcAPI()
    }

    @org.junit.jupiter.api.Test
    fun getUsers() {
        val users = this.arcApi.getUsers()
        assertTrue(users.count() > 100)
    }

    @org.junit.jupiter.api.Test
    fun getProjects() {
        val projects = this.arcApi.getProjects()
        assertTrue(projects.count() > 1)
    }
}