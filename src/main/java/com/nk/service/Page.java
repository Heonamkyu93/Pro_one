package com.nk.service;

public class Page {

	public int totlaPage(int totalData, int perPage) {
		return ((totalData - 1) / perPage) + 1;
	}

	public int endPage(int curPage, int perPage, int totalPage) {
		int endPage = (((curPage - 1) / perPage) + 1) * perPage;

		if (endPage > totalPage)
			endPage = totalPage;

		return endPage;
	}

	public int startPage(int curPage, int perPage) {
		return (((curPage - 1) / perPage) * perPage) + 1;
	}

	public boolean pre(int start) {
		return (start <= 1) ? false : true;
	}

	public boolean next(int totalPage, int endPage) {
		return (totalPage == endPage) ? false : true;
	}
}
