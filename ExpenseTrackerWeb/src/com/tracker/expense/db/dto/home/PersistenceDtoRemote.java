package com.tracker.expense.db.dto.home;

import com.tracker.expense.db.dto.SearchDto;
import com.tracker.expense.web.rest.service.OperationRestResult;

public interface PersistenceDtoRemote {
	
	public OperationRestResult upsertDto(Object dto);
	public OperationRestResult removeDto(SearchDto dto);
	public OperationRestResult getDto(SearchDto dto);
	public OperationRestResult listDto(SearchDto dto);

}
