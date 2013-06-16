/*
 * Orion Viewer - pdf, djvu, xps and cbz file viewer for android devices
 *
 * Copyright (C) 2011-2012  Michael Bogdanov
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package universe.constellation.orion.viewer.pdf;

import com.artifex.mupdf.MuPDFCore;
import universe.constellation.orion.viewer.DocumentWrapper;
import universe.constellation.orion.viewer.outline.OutlineItem;
import universe.constellation.orion.viewer.PageInfo;


/**
 * User: mike
 * Date: 16.10.11
 * Time: 17:57
 */
public class PdfDocument implements DocumentWrapper {

    private MuPDFCore core;

    public PdfDocument(String fileName) throws Exception {
        core = new MuPDFCore(fileName);
    }

    public boolean openDocument(String fileName) {
        return false;
    }

    public int getPageCount() {
        return core.getPageCount();
    }

    public PageInfo getPageInfo(int pageNum) {
        return core.getPageInfo(pageNum);
    }

    public int[] renderPage(int pageNumber, double zoom, int w, int h, int left, int top, int right, int bottom) {
        return core.renderPage(pageNumber, zoom, left, top, right - left, bottom - top);
    }

    public void destroy() {
        core.freeMemory();
    }

    public String getTitle() {
        return core.getInfo().title;
    }

    public void setContrast(int contrast) {
        core.setContrast(contrast);
    }

    public synchronized void gotoPage(int page) {
	    core.gotoPageInternal(page);
    }

    // MODIFIED
    public void setReflow(int reflow) {
        core.setReflow(reflow);
    }

    public void setReflowParameters(float zoom,
                                    int dpi,
                                    int columns,
                                    int bb_width,
                                    int bb_height,
                                    int m_top,
                                    int m_bottom,
                                    int m_left,
                                    int m_right,
                                    int default_trim,
                                    int wrap_text,
                                    int indent,
                                    int rotation,
                                    float margin,
                                    float word_space,
                                    float quality,
                                    int ocr_language,
                                    int white_thresh) {
        core.setReflowParameters(zoom, dpi, columns, bb_width, bb_height,
                                 m_top, m_bottom, m_left, m_right, default_trim,
                                 wrap_text, indent, rotation, margin, word_space,
                                 quality, ocr_language, white_thresh);
    }

	public void setThreshold(int threshold) {
		core.setThreshold(threshold);
	}

    public String getText(int pageNumber, int absoluteX, int absoluteY, int width, int height) {
        return core.getText(pageNumber, absoluteX, absoluteY, width, height);
    }

    public OutlineItem[] getOutline() {
        com.artifex.mupdf.OutlineItem [] items =  core.getOutlineInternal();
        if (items == null || items.length == 0) {
            return  null;
        } else {
            OutlineItem [] result = new OutlineItem[items.length];
            for (int i = 0; i < result.length; i++) {
                result[i] = new OutlineItem(items[i].level, items[i].title, items[i].page);
            }
            return result;
        }
	}
}
