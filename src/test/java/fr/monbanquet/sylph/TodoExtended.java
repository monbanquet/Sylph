/*
 * MIT License
 *
 * Copyright (c) 2019 Monbanquet.fr
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package fr.monbanquet.sylph;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class TodoExtended extends Todo {

    private Double a;
    private String b;
    private Long c;
    private List<String> d;
    private Map<String, Todo> e;
    private List<Todo> f;
    private LocalDateTime g;
    private LocalDate h;
    private LocalTime i;

    public Double getA() {
        return a;
    }

    public void setA(Double a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public Long getC() {
        return c;
    }

    public void setC(Long c) {
        this.c = c;
    }

    public List<String> getD() {
        return d;
    }

    public void setD(List<String> d) {
        this.d = d;
    }

    public Map<String, Todo> getE() {
        return e;
    }

    public void setE(Map<String, Todo> e) {
        this.e = e;
    }

    public List<Todo> getF() {
        return f;
    }

    public void setF(List<Todo> f) {
        this.f = f;
    }

    public LocalDateTime getG() {
        return g;
    }

    public void setG(LocalDateTime g) {
        this.g = g;
    }

    public LocalDate getH() {
        return h;
    }

    public void setH(LocalDate h) {
        this.h = h;
    }

    public LocalTime getI() {
        return i;
    }

    public void setI(LocalTime i) {
        this.i = i;
    }
}
