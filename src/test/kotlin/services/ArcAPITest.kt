package services

import org.junit.jupiter.api.Assertions.assertTrue

internal class ArcAPITest() {

    @org.junit.jupiter.api.Test
    fun getUsers() {
        val users = ArcAPI.getUsers()
        assertTrue(users.count() > 100)
    }

    @org.junit.jupiter.api.Test
    fun getProjects() {
        val projects = ArcAPI.getProjects()
        assertTrue(projects.count() > 1)
    }
}