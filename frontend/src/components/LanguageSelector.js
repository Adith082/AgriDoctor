import React, { useContext } from 'react'
import ButtonGroup from 'react-bootstrap/ButtonGroup';
import ToggleButton from 'react-bootstrap/ToggleButton';
import { LanguageContext } from '../contexts/LanguageContext';
import Image from 'react-bootstrap/Image';

function LanguageSelector() {

  const {isEN, setIsEN} = useContext(LanguageContext);

  return (
    <div>
        <ButtonGroup>
          <ToggleButton variant='outline-light'>
            <Image
                        width={25}
                        height={25}
                        className="mr-3"
                        src="https://cdn-icons-png.flaticon.com/512/888/888878.png" //Image Credit: FlatIcon
                    />
          </ToggleButton>
          <ToggleButton
            type="radio"
            variant='outline-info'
            name="radio"
            value={isEN}
            checked={isEN}
            onClick={(e)=>{setIsEN(true)}}
          >
            EN
          </ToggleButton>
          <ToggleButton
            type="radio"
            variant='outline-success'
            name="radio"
            value={isEN}
            checked={!isEN}
            onClick={(e)=>{setIsEN(false)}}
          >
            বাং
          </ToggleButton>
      </ButtonGroup>
    </div>
  )
}

export default LanguageSelector