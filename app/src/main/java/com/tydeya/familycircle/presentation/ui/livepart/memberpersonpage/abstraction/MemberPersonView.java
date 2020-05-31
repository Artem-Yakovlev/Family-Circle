package com.tydeya.familycircle.presentation.ui.livepart.memberpersonpage.abstraction;

import com.tydeya.familycircle.data.familymember.FamilyMemberDto;

public interface MemberPersonView {

    void setCurrentData(FamilyMemberDto dto);

    void setManagerMode(boolean managerMode);
}
