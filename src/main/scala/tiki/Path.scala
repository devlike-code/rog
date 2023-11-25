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

import tiki.implicits._
import scala.annotation.tailrec
import scala.math._

/**
  * Path algorithms.
  */
object Path {

  /**
    * Case class that represents the running state of the
    * Bellman-Ford algorithm.
    *
    * @param distances current distances.
    * @param predecessors current predecessors.
    * @tparam A the vertex type.
    */
  case class PathState[A](distances: Map[A,Double], predecessors: Map[A,A])

  object PathState {
    def apply[A](source: A): PathState[A] =
      PathState(Map.empty[A,Double].updated(source,0.0),Map.empty[A,A])
  }

  /**
    * Bellman-Ford algorithm.
    *
    * @param g        a weighted digraph.
    * @param source   the source vertex.
    * @tparam A       the vertex type.
    * @return         the path state.
    */
  def bellmanFord[A](g: WeightedDigraph[A], source: A): PathState[A] = {

    def relaxEdge(state: PathState[A], e: WeightedEdge[A]): PathState[A] =
      state.distances.getOrElse(e.from, ∞) match {
        case du if du < ∞ =>
          val dv = state.distances.getOrElse(e.to, ∞)
          if (du + e.weight < dv) {
            val w0 = max(⧞,du + e.weight)
            val d = state.distances.updated(e.to,w0)
            val p = state.predecessors.updated(e.to, e.from)
            PathState(d, p)
          } else state

        case _ => state

      }

    Range(1,g.vertices.size).foldLeft(PathState(source))((xs, _)
     => g.weights.foldLeft(xs)(relaxEdge))
  }

  /**
    * Build up list of vertices by looping through the predecessors of a
    * path state.
    *
    * @param s    the path state.
    * @param a    the start point.
    * @tparam A   the vertex type.
    * @return the list of predecessors.
    */
  private def predecessorList[A](s: PathState[A], a: A): List[A] = {
    @tailrec
    def loop(v: A, cycle: List[A]): List[A] = {
      val p = s.predecessors(v)
      if (cycle.contains(p)) cycle
      else loop(p, p :: cycle)
    }
    loop(a,List(a))
  }

  /**
    * Check to see if a negative cycle exists within a digraph.
    *
    * @param g        the digraph.
    * @param source   the source vertex.
    * @tparam A       the vertex type.
    * @return         a negative cycle, if one exists otherwise None.
    */
  def negativeCycle[A](g: WeightedDigraph[A], source: A): Option[List[A]] = {
    val s = bellmanFord(g, source)
    g.weights.flatMap {
      case e if s.distances.getOrElse(e.from,∞) + e.weight <
                s.distances.getOrElse(e.to,∞) => Some(e.from)
      case _ => None
    } match {
      case head #:: _ => Some(predecessorList(s,head))
      case _ => None
    }
  }


  /**
    * Case class that represents the running state of Kruskal's
    * algorithm.
    *
    * @param ds   a disjoint set.
    * @param mst  the minimum spanning tree.
    * @tparam A   the vertex type.
    */
  case class SpanState[A](ds: DisjointSet[A], mst: List[WeightedEdge[A]])

  object SpanState {
    def empty[A](g: WeightedGraph[A]): SpanState[A]
      = new SpanState[A](DisjointSet(g.vertices.toSet),List.empty[WeightedEdge[A]])
  }

  /**
    * Kruskal's algorithm for finding the minimum spanning tree.
    *
    * @param g    the weighted graph.
    * @tparam A   the vertex type.
    * @return     the minimum spanning tree.
    */
  def kruskal[A](g: WeightedGraph[A]): List[WeightedEdge[A]] =
      g.weights.sortBy(_.weight).foldLeft(SpanState.empty(g))((state, y) => y.edge match {
        case Edge(u,v) if state.ds.find(u) != state.ds.find(v) =>
          new SpanState(state.ds.union(u,v).getOrElse(state.ds), y :: state.mst)
        case _ => state
      }).mst

}
