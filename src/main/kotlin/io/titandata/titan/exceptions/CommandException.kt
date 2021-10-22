/*
 * Copyright (c) 2021 by Titan Project Contributors.. All rights reserved.
 */

package io.titandata.titan.exceptions

import java.io.IOException

class CommandException(message: String, val exitCode: Int, val output: String) : IOException(message)
