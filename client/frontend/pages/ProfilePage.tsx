import React, {useState} from 'react';
import '../src/ProfilePage.css';

const ProfilePage: React.FC = () => {
    return (
        <div className="page-content page-container" id="page-content">
            <div className="padding" style={{margin:'0'}}>
                <div className="row container d-flex justify-content-center" style={{margin:'0'}}>
                    <div className="col-xl-6 col-md-12">
                        <div className="card user-card-full">
                            <div className="row m-l-0 m-r-0">
                                <div className="col-sm-4 bg-c-lite-green user-profile">
                                    <div className="card-block text-center text-white">
                                        <div className="m-b-25">
                                            <img src="https://img.icons8.com/bubbles/100/000000/user.png"
                                                 className="img-radius" alt="User-Profile-Image"/>
                                        </div>
                                        <h6 className="f-w-600" style={{color: 'black'}}>Toni Kalashnika</h6>
                                        <p style={{color: 'black', fontSize: '10px'}}>tonikalashnika@gmail.com</p>
                                        <i className=" mdi mdi-square-edit-outline feather icon-edit m-t-10 f-16"></i>
                                    </div>
                                </div>
                                <div className="col-sm-8">
                                    <div className="card-block">
                                        <h6 className="m-b-20 p-b-5 b-b-default f-w-600">Information</h6>
                                        <div className="row">
                                            <div className="col-sm-6">
                                                <p className="m-b-10 f-w-600">Age</p>
                                                <h6 className="text-muted f-w-400">25</h6>
                                            </div>
                                            <div className="col-sm-6">
                                                <p className="m-b-10 f-w-600">Date of Birth</p>
                                                <h6 className="text-muted f-w-400">1998/05/12</h6>
                                            </div>
                                            <div className="col-sm-6">
                                                <p className="m-b-10 f-w-600">Address</p>
                                                <h6 className="text-muted f-w-400">Filipovtsi</h6>
                                            </div>
                                            <div className="col-sm-6">
                                                <p className="m-b-10 f-w-600">Looking to date</p>
                                                <h6 className="text-muted f-w-400">Female</h6>
                                            </div>
                                        </div>
                                        <h6 className="m-b-20 m-t-40 p-b-5 b-b-default f-w-600">Interests</h6>
                                        <p className="text-muted" style={{color: 'black', fontSize: '15px'}}>Reading,
                                            Wanking, Boxing</p>
                                        <h6 className="m-b-20 m-t-40 p-b-5 b-b-default f-w-600">Bio</h6>
                                        <h6 className="text-muted f-w-400">скъпа изглеждаш добре, ма не по+добре от хартията! за хартия бати лакомията. таа твойта бати мръсотията! ki + kilian по шията top тупалка, бати возията горчи, ама е бати вкусутията глей как блести, ебати газарията!</h6>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ProfilePage;
