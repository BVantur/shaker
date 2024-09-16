package sp.bvantur.inspektify.shared

import kotlinx.cinterop.ExperimentalForeignApi
import sp.bvantur.inspektify.inspektifyViewControllerInstance
import sp.bvantur.inspektify.ktor.PresentationType
import sp.bvantur.inspektify.shakedetektor.ShakeDetektorIOS

@OptIn(ExperimentalForeignApi::class)
actual fun configureUsageType(presentationType: PresentationType) {
    if (presentationType.isCustom()) return

    ShakeDetektorIOS().enableShakeDetektorWithCallback {
        if (inspektifyViewControllerInstance != null) return@enableShakeDetektorWithCallback

        startInspektifyWindow()
    }
}
