//
//  Texture.hpp
//  graph1
//
//  Created by apple on 2017/12/7.
//  Copyright © 2017年 GJN. All rights reserved.
//

#ifndef Texture_hpp
#define Texture_hpp

#include <stdio.h>

#pragma once

#include <GL/glew.h>
#include "Bitmap.hpp"

namespace tdogl {
    
    /**
     Represents an OpenGL texture
     */
    class Texture {
    public:
        /**
         Creates a texture from a bitmap.
         
         The texture will be loaded upside down because tdogl::Bitmap pixel data
         is ordered from the top row down, but OpenGL expects the data to
         be from the bottom row up.
         
         @param bitmap  The bitmap to load the texture from
         @param minMagFiler  GL_NEAREST or GL_LINEAR
         @param wrapMode GL_REPEAT, GL_MIRRORED_REPEAT, GL_CLAMP_TO_EDGE, or GL_CLAMP_TO_BORDER
         */
        Texture(const Bitmap& bitmap,
                GLint minMagFiler = GL_LINEAR,
                GLint wrapMode = GL_CLAMP_TO_EDGE);
        
        /**
         Deletes the texture object with glDeleteTextures
         */
        ~Texture();
        
        /**
         @result The texure object, as created by glGenTextures
         */
        GLuint object() const;
        
        /**
         @result The original width (in pixels) of the bitmap this texture was made from
         */
        GLfloat originalWidth() const;
        
        /**
         @result The original height (in pixels) of the bitmap this texture was made from
         */
        GLfloat originalHeight() const;
        
    private:
        GLuint _object;
        GLfloat _originalWidth;
        GLfloat _originalHeight;
        
        //copying disabled
        Texture(const Texture&);
        const Texture& operator=(const Texture&);
    };
    
}

#endif /* Texture_hpp */
