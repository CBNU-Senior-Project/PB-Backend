package com.phishing.notiservice.application.port.inbound;

import java.util.List;

public interface ViewNotiListUsecase {
    List<ViewNotiListResponse> viewNotiList(ViewNotiListQuery viewNotiListQuery);
}
