package com.wonders.frame.core.tags.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wonders.frame.core.tags.Between;

/**
 * @author mengjie
 *
 */
public class BetweenImplForDateValidator implements ConstraintValidator<Between, Date> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BetweenImplForDateValidator.class);

	private Date startDate;
	private Date endDate;
	private DateFormat dateFormat;

	public void initialize(Between annotation) {
		this.dateFormat = new SimpleDateFormat(annotation.format());
		try {
			this.startDate  = dateFormat.parse(annotation.startDate());
			this.endDate    = dateFormat.parse(annotation.endDate());
		} catch (ParseException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	public boolean isValid(Date value, ConstraintValidatorContext context) {
		LOGGER.debug("value = {}", dateFormat.format(value));
		return (startDate.getTime() <= value.getTime()) && (value.getTime() < endDate.getTime());
	}
}


