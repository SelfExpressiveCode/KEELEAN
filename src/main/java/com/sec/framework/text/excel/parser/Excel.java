package com.sec.framework.text.excel.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.avaje.ebean.Ebean;
import com.sec.framework.entity.BaseEntity;
import com.sec.framework.text.excel.exception.ExcelException;
import com.sec.framework.text.excel.parser.Excel.WorkSheet;

public class Excel implements Iterable<WorkSheet> {

	protected static Log log = LogFactory.getLog(Excel.class.getSimpleName());

	protected Workbook workBook = null;

	private List<WorkSheet> workSheets = new ArrayList<WorkSheet>();

	public void load(InputStream fileStream) {
		load(fileStream, 0);
	}

	public void load(InputStream fileStream, int startSheetIndex) {
		try {
			workBook = new HSSFWorkbook(fileStream);
			init(startSheetIndex);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private void init(int startSheetIndex) {
		for (int i = startSheetIndex; i < workBook.getNumberOfSheets(); i++) {
			workSheets.add(new WorkSheet(workBook.getSheetAt(i)));
		}
	}

	public Iterator<WorkSheet> iterator() {
		return workSheets.iterator();
	}

	public void parse(Class<? extends ExcelBean> excelBeanClass,
			Class<? extends BaseEntity> entityClass) {
		try {
			Ebean.beginTransaction();
			for (WorkSheet sheet : this) {
				for (WorkRow row : sheet) {
					ExcelBean bean = excelBeanClass.newInstance();
					bean.fill(row);
					bean.validate();
					bean.toEntity(entityClass).save();
				}
			}
			Ebean.commitTransaction();
		} catch (ExcelException e) {
			log.error(e.getMessage(), e);
			Ebean.rollbackTransaction();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Ebean.rollbackTransaction();
		} finally {
			Ebean.endTransaction();
		}
	}

	public class WorkSheet implements Iterable<WorkRow> {

		private List<WorkRow> rows = new ArrayList<WorkRow>();

		private Sheet sheet;

		private WorkSheet(Sheet sheet) {
			this.sheet = sheet;
			parse();
		}

		private void parse() {
			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				rows.add(new WorkRow(sheet.getRow(i)));
			}
		}

		public Iterator<WorkRow> iterator() {
			return rows.iterator();
		}

		public Object getCellContent(int row, int cell) {
			return rows.get(row).getWorkCell(cell).getContent();
		}

		public String getName() {
			return sheet.getSheetName();
		}

		public int rowCount() {
			return rows.size();
		}

		public WorkRow getRow(int i) {
			return rows.get(i);
		}
	}

	public class WorkRow {

		private List<WorkCell> cells = new ArrayList<WorkCell>();

		private Row row;

		private WorkRow(Row row) {
			this.row = row;
			parse();
		}

		private void parse() {

			for (int i = 0; i < row.getLastCellNum(); i++) {
				cells.add(new WorkCell(row.getCell(i)));
			}
		}

		public WorkCell getWorkCell(int cellIndex) {
			return cells.get(cellIndex);
		}

	}

	public class WorkCell {

		private Object content;

		private WorkCell(Cell cell) {
			parse(cell);
		}

		private void parse(Cell cell) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				content = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_ERROR:
				content = cell.getErrorCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				content = cell.getCellFormula();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				content = cell.getNumericCellValue();
				break;
			case Cell.CELL_TYPE_STRING:
				content = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BLANK:
				content = "";
				break;
			}
		}

		public Object getContent() {
			return content;
		}
	}

}
