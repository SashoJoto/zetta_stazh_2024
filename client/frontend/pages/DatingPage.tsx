import React from 'react';
import {Link} from 'react-router-dom';
import datePic from '../src/assets/dating_pic.png';
import '../src/DatingPage.css'

const DatingPage: React.FC = () => {
    return (
        <div className="container dating-page">
            <div className="date-pic-container">
                <img src={datePic} alt="Sample Date"
                     style={{margin:'25px',width: '400px', height: '70vh', zIndex: '999', borderRadius: '15px'}}/>
                <div className="date-pic-text">Toni Kalashnika, 25</div>
            </div>
            <div style={{display: 'flex', position: 'absolute', bottom: '1%', width:'400px', justifyContent:'space-between'}}>
                <button className="butoncheta btn mr-2" style={{marginRight:'150px'}}>
                    <img src="../src/assets/decline.png" alt="decline" style={{width: '80px', height: '80px'}}/>
                </button>
                {/*<button className="butoncheta btn mr-2">
                    <img src="../src/assets/favorite.png" alt="favorite" style={{width: '80px', height: '80px'}}/>
                </button>*/}
                <button className=" butoncheta btn">
                    <img src="../src/assets/love.png" alt="love" style={{width: '60px', height: '60px'}}/>
                </button>
            </div>

        </div>
    );
};

export default DatingPage;
