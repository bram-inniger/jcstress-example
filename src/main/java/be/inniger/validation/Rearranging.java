/*
 * Copyright (c) 2017, Red Hat Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package be.inniger.validation;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.IntResult2;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE_INTERESTING;

@SuppressWarnings("WeakerAccess")
@JCStressTest
@Description("The JVM is allowed to rearrange non-related statements as it sees fit before synchronization points happen")

@Outcome(id = "0, 0", expect = ACCEPTABLE_INTERESTING, desc = "JVM can rearrange statement withing a thread")
@Outcome(id = "1, 0", expect = ACCEPTABLE, desc = "Thread 1 is able to increment a before Thread 2 assigns it to r, but writes b away before Thread 2 can increment")
@Outcome(id = "0, 1", expect = ACCEPTABLE, desc = "Thread 2 is able to increment b before Thread 1 assigns it to r, but writes a away before Thread 1 can increment")
@Outcome(id = "1, 1", expect = ACCEPTABLE, desc = "Due to scheduling both Thread 1 and 2 first run their increment steps, then both assign the incremented a and b to r")

@State
public class Rearranging {

	private int a = 0;
	private int b = 0;

	@Actor
	public void actor1(IntResult2 r) {
		a++;
		r.r1 = b;
	}

	@Actor
	public void actor2(IntResult2 r) {
		b++;
		r.r2 = a;
	}
}
