package com.tydeya.familycircle.ui.livepart.memberpersonpage.details;

import com.tydeya.familycircle.domain.familymember.FamilyMember;
import com.tydeya.familycircle.domain.familymember.dto.FamilyMemberDto;
import com.tydeya.familycircle.ui.livepart.memberpersonpage.abstraction.MemberPersonPresenter;
import com.tydeya.familycircle.ui.livepart.memberpersonpage.abstraction.MemberPersonView;

class MemberPersonPresenterImpl implements MemberPersonPresenter {

    private MemberPersonView view;
    private FamilyMemberDto familyMemberDto;

    MemberPersonPresenterImpl(MemberPersonView view, FamilyMember familyMember) {
        this.familyMemberDto = new FamilyMemberDto(familyMember);
        this.view = view;
        view.setCurrentData(familyMemberDto);
    }
}
