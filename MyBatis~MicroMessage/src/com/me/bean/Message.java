package com.me.bean;

/**
 * @author Administrator
 *
 */
public class Message {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 指令名称
	 */
	private String  command;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 内容
	 */
	private String content;
	
	public Message() {
	}
	public Message(String id, String command, String description, String content) {
		this.id = id;
		this.command = command;
		this.description = description;
		this.content = content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Message [id=" + id + ", command=" + command + ", description=" + description + ", content=" + content
				+ "]";
	}
	
	
}
