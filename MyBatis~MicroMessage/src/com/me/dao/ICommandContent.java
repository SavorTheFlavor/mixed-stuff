package com.me.dao;

import java.util.List;

import com.me.bean.CommandContent;

/**
 * ��command_content���Ӧ�Ľӿ�
 *
 */
public interface ICommandContent {
	/**
	 * ��������
	 */
	public void insertBatch(List<CommandContent> contentList);
}
