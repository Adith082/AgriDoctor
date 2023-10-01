import React from 'react'
import Image from 'react-bootstrap/esm/Image'

const StandardImg = ({url}) => {
  return (
        <Image
            width={25}
            height={25}
            className="mr-3"
            src={url} //Any url being used here gets Image Credit: FlatIcon
            style={{paddingBottom:"5px"}}
        />
  )
}

export default StandardImg