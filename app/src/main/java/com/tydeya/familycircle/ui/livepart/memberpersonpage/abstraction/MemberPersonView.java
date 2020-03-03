package com.tydeya.familycircle.ui.livepart.memberpersonpage.abstraction;

import com.tydeya.familycircle.domain.familymember.dto.FamilyMemberDto;

public interface MemberPersonView {

    void setCurrentData(FamilyMemberDto dto);

    void setManagerMode(boolean managerMode);
}
