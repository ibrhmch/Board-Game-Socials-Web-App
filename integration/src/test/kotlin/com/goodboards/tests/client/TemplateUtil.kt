package com.goodboards.tests.client

import org.jsoup.nodes.Element
import kotlin.test.assertTrue

object TemplateUtil {
    fun checkHead(head: Element) {
        // Title
        assertTrue(head.select("title").isNotEmpty())
        assertTrue(head.select("title").text().equals("Team Slackers"))
    }

    fun checkNavBar(body: Element) {
        assertTrue(body.select("div#navbar-default").isNotEmpty())
        assertTrue(body.select("a[href='/games']").isNotEmpty())
        assertTrue(body.select("a[href='/contact']").isNotEmpty())
    }
}