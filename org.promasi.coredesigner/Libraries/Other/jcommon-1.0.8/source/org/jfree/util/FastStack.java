/**
 * ========================================
 * JCommon : a free Java report library
 * ========================================
 *
 * Project Info:  http://www.jfree.org/jcommon/
 *
 * (C) Copyright 2000-2006, by Object Refinery Limited and Contributors.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * ------------
 * $Id: FastStack.java,v 1.2 2006/12/11 12:02:27 taqua Exp $
 * ------------
 * (C) Copyright 2002-2006, by Object Refinery Limited.
 */

package org.jfree.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * A very simple unsynchronized stack. This one is faster than the
 * java.util-Version.
 *
 * @author Thomas Morgner
 */
public final class FastStack implements Serializable, Cloneable
{
  private Object[] contents;
  private int size;
  private int initialSize;

  public FastStack()
  {
    initialSize = 10;
  }

  public FastStack(int size)
  {
    initialSize = Math.max(1, size);
  }

  public boolean isEmpty()
  {
    return size == 0;
  }

  public int size()
  {
    return size;
  }

  public void push(Object o)
  {
    if (contents == null)
    {
      contents = new Object[initialSize];
      contents[0] = o;
      size = 1;
      return;
    }

    final int oldSize = size;
    size += 1;
    if (contents.length == size)
    {
      // grow ..
      final Object[] newContents = new Object[size + initialSize];
      System.arraycopy(contents, 0, newContents, 0, size);
      this.contents = newContents;
    }
    this.contents[oldSize] = o;
  }

  public Object peek()
  {
    if (size == 0)
    {
      throw new EmptyStackException();
    }
    return contents[size - 1];
  }

  public Object pop()
  {
    if (size == 0)
    {
      throw new EmptyStackException();
    }
    size -= 1;
    final Object retval = contents[size];
    contents[size] = null;
    return retval;
  }

  public Object clone()
  {
    try
    {
      FastStack stack = (FastStack) super.clone();
      if (contents != null)
      {
        stack.contents = (Object[]) contents.clone();
      }
      return stack;
    }
    catch (CloneNotSupportedException cne)
    {
      throw new IllegalStateException("Clone not supported? Why?");
    }
  }

  public void clear()
  {
    size = 0;
    if (contents != null)
    {
      Arrays.fill(contents, null);
    }
  }

  public Object get(final int index)
  {
    if (index >= size)
    {
      throw new IndexOutOfBoundsException();
    }
    return contents[index];
  }
}
