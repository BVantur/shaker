package sp.bvantur.inspektify.ktor.client.shared

import sp.bvantur.inspektify.ktor.PresentationType

internal expect fun configurePresentationType(presentationType: PresentationType)

internal expect fun startInspektifyWindow()

internal expect fun disposeInspektifyWindow()
