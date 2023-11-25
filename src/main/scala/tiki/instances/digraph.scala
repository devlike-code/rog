/*
 * Copyright (c) 2017
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package tiki
package instances


trait DigraphInstances {

  implicit class DigraphTranspose[T](g: Digraph[T]) extends Transpose[Digraph[T]] {
    override def transpose: Digraph[T] = new Digraph[T] {
      override def edges: Stream[Edge[T]] = g.edges.map(edge => Edge(edge.to,edge.from))
      override def predecessors(v: T): Stream[T] = g.successors(v)
      override def successors(v: T): Stream[T] = g.predecessors(v)
      override def contains(v: T): Boolean = g.contains(v)
      override def vertices: Stream[T] = g.vertices
    }
  }

  implicit class DigraphFilter[T](g: Digraph[T]) extends Filter[T,Digraph[T]] {
    override def filterNot(l: List[T]): Digraph[T] = new Digraph[T] {
      override def vertices: Stream[T] = g.vertices.filterNot(l.contains)
      override def edges: Stream[Edge[T]] = g.edges.filterNot(e=> l.contains(e.from) || l.contains(e.to))
      override def predecessors(v: T): Stream[T] = g.predecessors(v).filterNot(l.contains)
      override def successors(v: T): Stream[T] = g.successors(v).filterNot(l.contains)
      override def contains(v: T): Boolean = !l.contains(v) && g.contains(v)
    }
  }

}
