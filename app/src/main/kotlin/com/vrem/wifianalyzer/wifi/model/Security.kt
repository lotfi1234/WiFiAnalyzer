/*
 * WiFiAnalyzer
 * Copyright (C) 2015 - 2020 VREM Software Development <VREMSoftwareDevelopment@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.vrem.wifianalyzer.wifi.model

import com.vrem.util.EMPTY
import com.vrem.wifianalyzer.R
import java.util.*

private const val RSN = "RSN"

enum class Security(val imageResource: Int, val additional: String = String.EMPTY) {
    NONE(R.drawable.ic_lock_open),
    WPS(R.drawable.ic_lock_outline),
    WEP(R.drawable.ic_lock_outline),
    WPA(R.drawable.ic_lock),
    WPA2(R.drawable.ic_lock),
    WPA3(R.drawable.ic_lock, RSN);

    companion object {
        fun findAll(capabilities: String): Set<Security> =
                parse(capabilities).mapNotNull(transform()).toSortedSet().ifEmpty { setOf(NONE) }

        fun findOne(capabilities: String): Security = findAll(capabilities).first()

        private fun transform(): (String) -> Security? = {
            try {
                enumValueOf<Security>(it)
            } catch (e: IllegalArgumentException) {
                enumValues<Security>().find { security -> security.additional == it }
            }
        }

        private fun parse(capabilities: String): List<String> =
                capabilities
                        .toUpperCase(Locale.getDefault())
                        .replace("][", "-")
                        .replace("]", "")
                        .replace("[", "")
                        .split("-")
    }

}
