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


/**
  * Base representation for a graph.
  *
  * @tparam A the vertex type.
  */
trait Graph[A] {
  /**
    * Return a stream of vertices.
    *
    * @return the stream of vertices.
    */
  def vertices: Stream[A]

  /**
    * Returns a stream of edges.
    *
    * @return the stream of edges.
    */
  def edges: Stream[Edge[A]]
}

/**
  * Defines a 'directed' interface that some graph
  * representations may support.
  */
trait Directed[A] {
  /**
    * Returns true if the vertex is contained in the graph.
    *
    * @param v  the vertex.
    * @return true, if the vertex is in the graph, false otherwise.
    */
  def contains(v: A): Boolean

  /**
    * Returns all vertices that the given vertex has an out-edge to.
    *
    * @param v  the vertex.
    * @return the set of vertices that the given vertex has an out-edge to.
    */
  def successors(v: A): Stream[A]

  /**
    * Returns all vertices that have an out-edge to the given vertex.
    *
    * @param v the vertex.
    * @return the set of vertices that have an out-edge to the given vertex.
    */
  def predecessors(v: A): Stream[A]
}

/**
  * Weighted graphs should be able to return weighted edges.
  *
  * @tparam A the vertex type.
  */
trait Weighted[A] {
  def weights: Stream[WeightedEdge[A]]
  def edges: Stream[Edge[A]] = weights.map(_.edge)
}

/**
  * A directed graph (a graph that supports the `Directed` interface.
  *
  * @tparam A the vertex type.
  */
trait Digraph[A] extends Graph[A] with Directed[A] {}

/**
  * A weighted undirected graph is a graph that returns weighted edges.
  * (Doesn't support the `Directed` interface).
  * @tparam A the vertex type.
  */
trait WeightedUndirectedGraph[A] extends Graph[A] with Weighted[A]

/**
  * Weighted digraph, a digraph that returns weighted edges.
  *
  * @tparam A the vertex type.
  */
trait WeightedDigraph[A] extends Digraph[A] with Weighted[A]
/**
  * A weighted graph class.
  *
  * @tparam A the vertex type.
  */
trait WeightedGraph[A] extends Graph[A] with Weighted[A]
