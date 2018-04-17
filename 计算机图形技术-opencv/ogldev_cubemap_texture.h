//
//  ogldev_cubemap_texture.h
//  graph1
//
//  Created by apple on 2017/12/31.
//  Copyright © 2017年 GJN. All rights reserved.
//

#ifndef ogldev_cubemap_texture_h
#define ogldev_cubemap_texture_h

class CubemapTexture
{
public:
    
    CubemapTexture(const string& Directory,
                   const string& PosXFilename,
                   const string& NegXFilename,
                   const string& PosYFilename,
                   const string& NegYFilename,
                   const string& PosZFilename,
                   const string& NegZFilename);
    
    ~CubemapTexture();
    
    bool Load();
    
    void Bind(GLenum TextureUnit);
    
private:
    
    string m_fileNames[6];
    GLuint m_textureObj;
};

#endif /* ogldev_cubemap_texture_h */
