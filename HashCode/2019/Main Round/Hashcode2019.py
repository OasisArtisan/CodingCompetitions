import random as rand
import numpy as np
filenames = ['a_example','b_lovely_landscapes',
             'c_memorable_moments',
             'd_pet_pictures',
             'e_shiny_selfies']
exe = '.txt'
t0 = 1
t1 = 0
max_iter = 100
def get_common_tags(image1, image2):
    return image1['tags'] & image2['tags']


def get_unique_tags(image1, image2):
    return image1['tags'] | image2['tags']


for filename in filenames:
    print(filename + '-----------------------')
    # Input------------------
    with open(filename + exe,'r') as file:
        imagen = int(file.readline())
        images = list()
        for i,line in enumerate(file):
            tokens = line[:-1].split(' ')
            images.append({'id':i,'ori': tokens[0], 'tagn': int(tokens[1]), 'tags': set()})
            for token in tokens[2:]:
                images[i]['tags'].add(token)

    #Create slides--------------
    slides = []
    print('Creating slides')
    while len(images) > 0:
        if len(images) % 100 == 0:
            print('images {}'.format(len(images)))

        image = images.pop()
        if image['ori'] == 'H':
            # Create slide immedietly
            slides.append({'id':[image['id']],
                           'tags':image['tags'],
                           'tagn': image['tagn']})
        else:
            # Find partner for vertical image to create slide
            best_candidate = None
            index = None
            for i in range(len(images)):
                if images[i]['ori'] == 'H':
                    continue
                common = len(get_common_tags(image,images[i]))
                if common == 0:
                    best_candidate = (images[i],common)
                    index = i
                    break
                if best_candidate is None or common < best_candidate[1]:
                    best_candidate = (images[i],common)
                    index = i
            if best_candidate is None:
                continue
            last = images.pop()
            if index != len(images):
                images[index] = last
            combined_tags = get_unique_tags(image,best_candidate[0])
            slides.append({'id':[image['id'],best_candidate[0]['id']],
                           'tags': combined_tags,
                           'tagn':len(combined_tags)})
    # Sorting slides based on length
    print('Sorting slides')
    slides.sort(key = lambda slide: slide['tagn'])
    print('Ordering slides')
    # Ordering slides----------------------
    def get_score(slide1, slide2):
        diff = np.abs(len(slide1["tags"]) - len(slide2['tags']))
        ab = len(slide1["tags"] & slide2['tags'])
        anotb = len(slide1["tags"] - slide2['tags'])
        notab = len(slide2["tags"] - slide1['tags'])
        m = min(ab, anotb, notab)
        result = t0 * m - t1 * diff
        return result

    def get_perfect_score(slide):
        return t0 * int(slide['tagn'] / 2)

    ordered_slides = [slides.pop()]

    while len(slides) > 0:
        if len(slides) % 100 == 0:
            print('images {}'.format(len(slides)))
        perfectscore = get_perfect_score(ordered_slides[-1])
        best_candidate = (slides[0],get_score(slides[0],ordered_slides[-1]))
        index = 0
        for i,slide in enumerate(slides[1:]):
            if i > max_iter:
                break
            s = get_score(slides[i], ordered_slides[-1])
            if s > best_candidate[1]:
                best_candidate = (slides[i],s)
                index = i
            if s == perfectscore:
                break
        ordered_slides.append(best_candidate[0])
        last = slides.pop()
        if index != len(slides):
            slides[index] = last

    # Output--------------------
    slides = ordered_slides
    with open(filename + '_out' + exe,'w') as f:
        f.write(str(len(slides))+ '\n')
        for slide in slides:
            if len(slide['id']) == 2:
                f.write(str(slide['id'][0]) + ' ' + str(slide['id'][1]) + '\n')
            else:
                f.write(str(slide['id'][0]) + '\n')

