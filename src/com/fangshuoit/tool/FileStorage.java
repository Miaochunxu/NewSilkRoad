package com.fangshuoit.tool;

import java.io.File;

import android.os.Environment;

public interface FileStorage {
	public static final String ARECORD_NAME = "Android" + File.separator
			+ "data" + File.separator + "carserver" + File.separator + "files";
	public static final String CACHE_DIR_NAME = "pic_cache";
	public static final String HTML_DIR_NAME = "bless_html_cache";
	public static final String PICTURE_DIR_NAME = "picture";

	public static final String ARECORD_CACHE_DIR = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ ARECORD_NAME
			+ File.separator + CACHE_DIR_NAME + File.separator;

	public static final String HTML_CACHE_DIR = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ ARECORD_NAME
			+ File.separator + HTML_DIR_NAME + File.separator;

	public static final String PICTURE_CACHE_DIR = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ ARECORD_NAME
			+ File.separator + PICTURE_DIR_NAME + File.separator;

}
