package com.tydeya.familycircle.presentation.ui.livepart.memberpersonpage.details;

import com.google.firebase.auth.FirebaseAuth;
import com.tydeya.familycircle.data.familymember.FamilyMember;
import com.tydeya.familycircle.data.familymember.FamilyMemberDto;
import com.tydeya.familycircle.presentation.ui.livepart.memberpersonpage.abstraction.MemberPersonPresenter;
import com.tydeya.familycircle.presentation.ui.livepart.memberpersonpage.abstraction.MemberPersonView;

class MemberPersonPresenterImpl implements MemberPersonPresenter {

    private MemberPersonView view;
    private FamilyMemberDto familyMemberDto;
    private String fullPhoneNumber;

    MemberPersonPresenterImpl(MemberPersonView view, FamilyMember familyMember) {
        this.familyMemberDto = new FamilyMemberDto(familyMember);
        this.view = view;
        this.fullPhoneNumber = familyMember.getFullPhoneNumber();
        setPersonPageView();
    }

    private void setPersonPageView() {
        view.setCurrentData(familyMemberDto);
        view.setManagerMode(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().equals(fullPhoneNumber));
    }


}
