package com.me.dao;

import java.util.List;

import com.me.bean.CommandContent;

/**
 * 与command_content表对应的接口
 *
 */
public interface ICommandContent {
	/**
	 * 批量新增
	 */
	public void insertBatch(List<CommandContent> contentList);
}
