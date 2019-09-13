package com.tracker.expense.db.dto.home;

import java.util.List;

import com.tracker.expense.web.rest.service.OperationRestResult;

public interface TransaccionAttachmentRemote {

	OperationRestResult removeAttachment(int idtransaccion, String tipoComprobante);
	OperationRestResult addAttachment(int idtransaccion,String tipoComprobante, String fileName);
	public List<String> getAttachments(int idtransaccion);
}